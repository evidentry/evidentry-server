package io.github.evidentry.repository.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Embeddable
public class AuditEventChange implements Serializable {
    @NotNull
    private String field;
    @NotNull
    private String type;
    @NotNull
    private String value;
}
