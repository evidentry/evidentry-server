package io.github.evidentry.repository.entity;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Entity
@Table(name = "audit_event", indexes = {
        @Index(columnList = "entity_id, entity_type"),
        @Index(columnList = "entity_id, entity_type, created_at"),
        @Index(columnList = "created_at"),
        @Index(columnList = "auditor_id"),
})
@EntityListeners(AuditingEntityListener.class)
public class AuditEvent implements Serializable {
    @Id
    @Column(name = "id")
    @UuidGenerator
    private UUID id;
    @NotNull
    @Column(name = "entity_id", nullable = false, updatable = false)
    private String entityId;
    @NotNull
    @Column(name = "entity_type", nullable = false, updatable = false)
    private String entityType;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @NotNull
    @Column(name = "auditor_id", nullable = false, updatable = false)
    private String auditorId;
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @ElementCollection
    @CollectionTable(
            name = "audit_event_changes",
            joinColumns = @JoinColumn(name = "audit_event_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"audit_event_id", "field"})
    )
    @AttributeOverride(name = "field", column = @Column(name = "field", nullable = false))
    @AttributeOverride(name = "type", column = @Column(name = "type", nullable = false))
    @AttributeOverride(name = "value", column = @Column(name = "_value"))
    private Set<AuditEventChange> changes;
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "_value", nullable = false)
    @CollectionTable(name = "audit_event_attributes", joinColumns = @JoinColumn(name = "audit_event_id"))
    private Map<@NotBlank String, @NotNull String> attributes;
}
