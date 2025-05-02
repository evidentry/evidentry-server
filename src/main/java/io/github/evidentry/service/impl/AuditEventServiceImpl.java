package io.github.evidentry.service.impl;

import io.github.evidentry.model.AuditEventCreateRequest;
import io.github.evidentry.model.AuditEventResponse;
import io.github.evidentry.model.params.AuditEventFilterParams;
import io.github.evidentry.repository.AuditEventRepository;
import io.github.evidentry.repository.entity.AuditEvent;
import io.github.evidentry.repository.entity.AuditEventChange;
import io.github.evidentry.repository.entity.AuditEvent_;
import io.github.evidentry.service.AuditEventService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: refactor needed!!
@Service
@RequiredArgsConstructor
public class AuditEventServiceImpl implements AuditEventService {
    private final AuditEventRepository auditEventRepository;

    @Override
    public AuditEventResponse create(AuditEventCreateRequest request) {
        return Optional.of(request)
                .map(r -> {
                    var auditEvent = AuditEvent.builder()
                            .entityType(r.entityType())
                            .auditorId(r.auditorId())
                            .entityId(r.entityId())
                            .build();
                    auditEvent.setAttributes(request.attributes());
                    auditEvent.setChanges(
                            request.changes()
                                    .stream()
                                    .map(entry -> AuditEventChange.builder()
                                            .field(entry.field())
                                            .type(ObjectUtils.toString(entry.type(), () -> entry.value().getClass().getName()))
                                            .value(entry.value().toString())
                                            .build())
                                    .collect(Collectors.toSet())
                    );

                    return auditEvent;
                })
                .map(auditEventRepository::save)
                .map(e -> AuditEventResponse.builder()
                        .id(e.getId())
                        .createdAt(LocalDateTime.ofInstant(e.getCreatedAt(), ZoneId.systemDefault()))
                        .entityType(e.getEntityType())
                        .entityId(e.getEntityId())
                        .auditorId(e.getAuditorId())
                        .changes(e.getChanges()
                                .stream()
                                .map(c -> AuditEventResponse.Change.builder()
                                        .field(c.getField())
                                        .value(c.getValue())
                                        .type(c.getType())
                                        .build())
                                .collect(Collectors.toSet()))
                        .attributes(e.getAttributes())
                        .build())
                .orElseThrow();
    }

    @Override
    public PagedModel<AuditEventResponse> find(AuditEventFilterParams params, Pageable pageable) {
        var specs = Specification.<AuditEvent>allOf(
                (root, cq, cb) -> root.get(AuditEvent_.auditorId).in(params.auditorIds()),
                (root, cq, cb) -> root.get(AuditEvent_.entityType).in(params.entityTypes()),
                (root, cq, cb) -> root.get(AuditEvent_.entityId).in(params.entityIds())
        );
        var page = auditEventRepository.findAll(specs, pageable)
                .map(e -> AuditEventResponse.builder()
                        .id(e.getId())
                        .createdAt(LocalDateTime.ofInstant(e.getCreatedAt(), ZoneId.systemDefault()))
                        .entityType(e.getEntityType())
                        .entityId(e.getEntityId())
                        .auditorId(e.getAuditorId())
                        .changes(e.getChanges()
                                .stream()
                                .map(c -> AuditEventResponse.Change.builder()
                                        .field(c.getField())
                                        .value(c.getValue())
                                        .type(c.getType())
                                        .build())
                                .collect(Collectors.toSet()))
                        .attributes(e.getAttributes())
                        .build());

        return new PagedModel<>(page);
    }
}
