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
@lombok.Builder(builderClassName = "Builder")
@Accessors(fluent = true, chain = true)
@Entity
@Table(name = "audit_event_attributes")
@IdClass(AuditEventAttribute.Identifier.class)
public class AuditEventAttribute implements Serializable {
    @Id
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "_value", nullable = false)
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
        private String name;

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;

            Identifier that = (Identifier) o;
            return Objects.equals(auditEvent.id(), that.auditEvent.id()) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(auditEvent.id());
            result = 31 * result + Objects.hashCode(name);
            return result;
        }
    }
}
