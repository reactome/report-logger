package org.reactome.server.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Guilherme S Viteri <gviteri@ebi.ac.uk>
 */

@Entity
@Table(
        name = "target_resource",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "UK_TR_NAME")}
)
public class TargetResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alternative_names")
    private String alternativeNames;

    public TargetResource() {
    }

    public TargetResource(String name) {
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TargetResource that = (TargetResource) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
