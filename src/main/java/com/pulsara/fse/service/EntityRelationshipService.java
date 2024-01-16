package com.pulsara.fse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pulsara.fse.exceptions.FSEServiceException;
import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.models.EntityRelationshipModel;
import com.pulsara.fse.models.Patient;
import com.pulsara.fse.models.RelationshipType;
import com.pulsara.fse.repository.EntityRelationshipRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing entity relationships.
 * This service provides methods to interact with and manipulate entity
 * relationships.
 *
 * @since 1.0
 */
@Service
public class EntityRelationshipService {

    private final EntityRelationshipRepository entityRelationshipRepository;
    private static final Logger logger = LoggerFactory.getLogger(EntityRelationshipService.class);

    /**
     * Constructs an EntityRelationshipService with the specified
     * EntityRelationshipRepository.
     *
     * @param entityRelationshipRepository The repository used for accessing entity
     *                                     relationships.
     */
    @Autowired
    public EntityRelationshipService(EntityRelationshipRepository entityRelationshipRepository) {
        this.entityRelationshipRepository = entityRelationshipRepository;
    }

    /**
     * Retrieves relationships based on the provided source entity.
     *
     * @param sourceEntity The source entity for which relationships are to be
     *                     retrieved.
     * @return A list of entity relationships associated with the provided source
     *         entity.
     * @throws FSEServiceException If an error occurs during the retrieval process.
     */
    @Transactional
    public List<EntityRelationshipModel> getRelationshipsBySourceEntity(EntityModel sourceEntity) {
        try {
            // Validate sourceEntity
            if (sourceEntity == null) {
                logger.error("sourceEntity is null");
                throw new FSEServiceException("Source entity not found", HttpStatus.NOT_FOUND);
            }

            return entityRelationshipRepository.findBySourceEntity(sourceEntity);
        } catch (Exception e) {
            logger.error("An error occurred ", e);
            throw new FSEServiceException("Error retrieving relationships", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves relationships based on the provided source entity and relationship
     * types.
     *
     * @param sourceEntity      The source entity for which relationships are to be
     *                          retrieved.
     * @param relationshipTypes A set of relationship types for which relationships
     *                          are to be retrieved.
     * @return A list of entity relationships associated with the provided source
     *         entity and relationship types.
     * @throws FSEServiceException If an error occurs during the retrieval process.
     */
    @Transactional
    public List<EntityRelationshipModel> getRelationshipsBySourceEntityAndRelationshipTypes(EntityModel sourceEntity,
            Set<RelationshipType> relationshipTypes) {
        try {
            // Validate sourceEntity and relationshipTypes
            if (sourceEntity == null) {
                logger.error("sourceEntity is null");
                throw new FSEServiceException("Source entity not found", HttpStatus.NOT_FOUND);
            }

            if (relationshipTypes == null) {
                logger.error("relationshipTypes is null");
                throw new FSEServiceException("Relationship Types not found", HttpStatus.NOT_FOUND);
            }

            return entityRelationshipRepository.findBySourceEntityAndRelationshipTypes(sourceEntity, relationshipTypes);

        } catch (Exception e) {
            logger.error("An error occurred", e);
            throw new FSEServiceException("Unable to retrieve relationships at this time. Please try again!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves patients based on the provided source entity, destination entity,
     * and relationship type.
     *
     * @param sourceEntity      The source entity for which patients are to be
     *                          retrieved.
     * @param destinationEntity The destination entity for which patients are to be
     *                          retrieved.
     * @param relationshipType  The relationship type for which patients are to be
     *                          retrieved.
     * @return A list of patients associated with the provided source entity,
     *         destination entity, and relationship type.
     */
    @Transactional
    public List<Patient> getPatientsBySourceAndDestinationAndRelationshipType(
            EntityModel sourceEntity, EntityModel destinationEntity, RelationshipType relationshipType) {
        List<EntityRelationshipModel> relationships = entityRelationshipRepository
                .findBySourceEntityAndDestinationEntityAndRelationshipType(
                        sourceEntity, destinationEntity, relationshipType);

        // Extract patients from relationships
        return relationships.stream()
                .map(EntityRelationshipModel::getPatient)
                .collect(Collectors.toList());
    }

    /**
     * Maps an entity relationship to a structured map with source, destination, and
     * patient types.
     *
     * @param relationship The entity relationship to be mapped.
     * @return A structured map representing the entity relationship.
     */
    public Map<String, Object> mapRelationship(EntityRelationshipModel relationship) {
        Map<String, Object> mappedRelationship = new LinkedHashMap<>();
        mappedRelationship.put("source", mapEntity(relationship.getSourceEntity()));
        mappedRelationship.put("destination", mapEntity(relationship.getDestinationEntity()));
        mappedRelationship.put("patient_types", this.getPatientsBySourceAndDestinationAndRelationshipType(
                relationship.getSourceEntity(),
                relationship.getDestinationEntity(),
                relationship.getRelationshipType()));
        return mappedRelationship;
    }

    /**
     * Maps an entity to a structured map with id, name, and type.
     *
     * @param entity The entity to be mapped.
     * @return A structured map representing the entity.
     */
    private Map<String, Object> mapEntity(EntityModel entity) {
        Map<String, Object> mappedEntity = new LinkedHashMap<>();
        mappedEntity.put("id", entity.getId());
        mappedEntity.put("name", entity.getName());
        mappedEntity.put("type", entity.getEntityType());
        return mappedEntity;
    }

    /**
     * Retrieves relationships based on the provided source entity and a collection
     * of relationship types.
     *
     * @param sourceEntity            The source entity for which relationships are
     *                                to be retrieved.
     * @param relationshipTypeStrings A collection of relationship types for which
     *                                relationships are to be retrieved.
     * @return A list of entity relationships associated with the provided source
     *         entity and relationship types.
     */
    public List<EntityRelationshipModel> getRelationshipsBySourceEntityAndRelTypes(
            EntityModel sourceEntity,
            Set<String> relationshipTypeStrings) {
        Set<RelationshipType> relationshipTypes = relationshipTypeStrings.stream()
                .map(RelationshipType::valueOf) // Convert each string to enum
                .collect(Collectors.toSet());

        return getRelationshipsBySourceEntityAndRelationshipTypes(sourceEntity, relationshipTypes);
    }
}
