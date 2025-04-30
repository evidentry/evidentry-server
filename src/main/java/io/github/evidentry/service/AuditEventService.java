package io.github.evidentry.service;

import io.github.evidentry.model.AuditEventCreateRequest;
import io.github.evidentry.model.AuditEventResponse;
import io.github.evidentry.model.params.AuditEventFilterParams;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface AuditEventService {
    AuditEventResponse create(AuditEventCreateRequest request);

    PagedModel<AuditEventResponse> find(AuditEventFilterParams params, Pageable pageable);
}
