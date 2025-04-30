package io.github.evidentry.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Builder(builderClassName = "Builder")
public record AuditEventResponse(
        UUID id,
        String entityType,
        String entityId,
        String auditorId,
        LocalDateTime createdAt,
        Collection<Change> changes,
        Map<String, String> attributes
) {
    @lombok.Builder(builderClassName = "Builder")
    public record Change(String field, String type, Object value) {}
}
