package io.github.evidentry.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Accessors(fluent = true, chain = true)
@Entity
@Table(name = "audit_event_changes")
@IdClass(AuditEventChange.Identifier.class)
public class AuditEventChange implements Serializable {
    @Id
    @NotNull
    @Column(name = "field")
    private String field;
    @NotNull
    @Column(name = "type")
    private String type;
    @NotNull
    @Column(name = "_value")
    private String value;
    @Id
    @NotNull
    @JoinColumn(name = "audit_event_id", updatable = false)
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    private AuditEvent auditEvent;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class Identifier implements Serializable {
        private AuditEvent auditEvent;
        private String field;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            Identifier that = (Identifier) o;
            return Objects.equals(auditEvent.id(), that.auditEvent.id()) && Objects.equals(field, that.field);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(auditEvent.id());
            result = 31 * result + Objects.hashCode(field);
            return result;
        }
    }
}
