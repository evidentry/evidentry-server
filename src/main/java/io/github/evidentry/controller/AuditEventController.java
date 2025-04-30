package io.github.evidentry.controller;

import io.github.evidentry.model.AuditEventCreateRequest;
import io.github.evidentry.model.AuditEventResponse;
import io.github.evidentry.model.params.AuditEventFilterParams;
import io.github.evidentry.repository.entity.AuditEvent_;
import io.github.evidentry.service.AuditEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(AuditEventController.CONTEXT_PATH)
@RequiredArgsConstructor
public class AuditEventController {
    public static final String CONTEXT_PATH = "/audit-event";
    private final AuditEventService auditEventService;

    @PostMapping
    public ResponseEntity<AuditEventResponse> create(@RequestBody AuditEventCreateRequest request) {
        var response = auditEventService.create(request);
        return ResponseEntity.created(URI.create("/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedModel<AuditEventResponse>> find(
            @RequestParam
            AuditEventFilterParams params,
            @PageableDefault(size = 25, sort = AuditEvent_.CREATED_AT, direction = Sort.Direction.DESC)
            @RequestParam
            Pageable pageable
    ) {
        return ResponseEntity.ok(auditEventService.find(params, pageable));
    }
}
