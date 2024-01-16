package com.pulsara.fse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an Entity
 */
@Entity
@Table(name = "TBL_ENTITIES")
public class EntityModel {

    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private EntityType entityType;

    // getters and setters

    /**
     * Gets the ID of the Entity
     *
     * @return The ID of the Entity.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the Name of the Entity
     * 
     * @return The name of the Entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Type of the Entity
     * 
     * @return EntityType representing the type of the entity
     */
    public EntityType getEntityType() {
        return entityType;
    }

    public void setId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
