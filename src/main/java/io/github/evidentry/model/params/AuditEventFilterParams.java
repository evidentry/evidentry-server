package io.github.evidentry.model.params;

import java.util.Collection;

public record AuditEventFilterParams(
        Collection<String> entityIds,
        Collection<String> entityTypes,
        Collection<String> auditorIds
) {
}
