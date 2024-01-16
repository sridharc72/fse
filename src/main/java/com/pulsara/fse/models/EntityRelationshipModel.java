package com.pulsara.fse.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pulsara.fse.repository.EntityRelationshipRepository;
import com.pulsara.fse.service.EntityRelationshipService;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents an entity relationship between two entities.
 * This class defines the structure and behavior of entity relationships.
 *
 * @see EntityModel
 * @see RelationshipType
 * @see EntityRelationshipRepository
 * @see EntityRelationshipService
 * @author Sridhar Chodavarapu
 * @version 1.0
 */

@Entity
@Table(name = "TBL_ENTITY_RELATIONSHIPS")
@JsonPropertyOrder({ "sourceEntity", "destinationEntity" })
public class EntityRelationshipModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_entity_id", referencedColumnName = "id")
    private EntityModel sourceEntity;

    @ManyToOne
    @JoinColumn(name = "destination_entity_id", referencedColumnName = "id")
    private EntityModel destinationEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type")
    private RelationshipType relationshipType;

    @Embedded
    private Patient patient;

    // Other properties...

    // Getters and setters

    /**
     * Gets the name of the Entity Relationship
     * 
     * @return ID of the Entity Relationship
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the source entity in the relationship.
     * This method retrieves the source entity associated with the entity
     * relationship.
     *
     * @return The source entity in the relationship.
     * @see EntityModel
     * @since 1.0
     */
    public EntityModel getSourceEntity() {
        return sourceEntity;
    }

    /**
     * Gets the destination entity in the relationship.
     * This method retrieves the destination entity associated with the entity
     * relationship.
     *
     * @return The destination entity in the relationship.
     * @see EntityModel
     * @since 1.0
     */
    public EntityModel getDestinationEntity() {
        return destinationEntity;
    }

    /**
     * Gets the relationship type between the source and destination entities in the
     * relationship.
     * This method retrieves the relationship type associated with the entity
     * relationship.
     *
     * @return The relationship type in the relationship.
     * @see EntityModel
     * @since 1.0
     */

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    /**
     * Gets the patient information associated with the entity relationship.
     * This method retrieves the patient information, including patient type,
     * contact phone, and instructions,
     * associated with the entity relationship.
     *
     * @return The patient information.
     * @see Patient
     * @since 1.0
     */
    public Patient getPatient() {
        return patient;
    }

    public void setSourceEntity(EntityModel sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public void setDestinationEntity(EntityModel destinationEntity) {
        this.destinationEntity = destinationEntity;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }
}
