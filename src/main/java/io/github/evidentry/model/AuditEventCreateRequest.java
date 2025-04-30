package io.github.evidentry.model;

import java.util.List;
import java.util.Map;

public record AuditEventCreateRequest(
        String entityType,
        String entityId,
        String auditorId,
        List<Change> changes,
        Map<String, String> attributes
) {
    public record Change(String field, String type, Object value) {}
}
