package com.pulsara.fse.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.models.EntityRelationshipModel;
import com.pulsara.fse.models.RelationshipType;
import com.pulsara.fse.service.EntityRelationshipService;
import com.pulsara.fse.service.EntityService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entity-relationships")
public class EntityRelationshipController {

    private final EntityRelationshipService entityRelationshipService;
    private final EntityService entityService;
    private static final Logger logger = LoggerFactory.getLogger(EntityRelationshipService.class);

    public EntityRelationshipController(EntityRelationshipService entityRelationshipService,
            EntityService entityService) {
        this.entityRelationshipService = entityRelationshipService;
        this.entityService = entityService;
    }

    /**
     * Retrieves relationships based on the provided source entity ID and optional
     * relationship types.
     *
     * @param sourceEntityId    The ID of the source entity.
     * @param relationshipTypes An optional set of strings in the form of comma
     *                          separated values representing relationship types to
     *                          filter.
     * @return ResponseEntity containing a map of grouped relationships or an error
     *         response.
     */
    @GetMapping("/by-source/{sourceEntityId}")
    public ResponseEntity<Map<String, Map<Object, Set<Map<String, Object>>>>> getRelationshipsBySourceEntity(
            @PathVariable Long sourceEntityId,
            @RequestParam(required = false) Set<String> relationshipTypes) {

        // validate the request parameters
        if (!validateRequest(sourceEntityId, relationshipTypes)) {
            logger.error("Could not validate the request. Check the request parameters and try again");
            return ResponseEntity.badRequest().body(null);
        }

        // Retrieve the source entity using the EntityService
        EntityModel sourceEntity = entityService.getEntityById(sourceEntityId);

        // Check if the source entity exists
        if (sourceEntity == null) {
            // Handle the case where the source entity is not found
            logger.info("Could not find the provided source entity with ID {}", sourceEntityId);
            return ResponseEntity.notFound().build();
        }

        // Retrieve relationships based on the source entity and optional relationship
        // types
        List<EntityRelationshipModel> relationships;
        if (relationshipTypes != null && !relationshipTypes.isEmpty()) {
            // Filter relationships by provided relationship types
            relationships = entityRelationshipService.getRelationshipsBySourceEntityAndRelTypes(
                    sourceEntity, relationshipTypes);
        } else {
            // Retrieve all relationships if no types are provided
            relationships = entityRelationshipService.getRelationshipsBySourceEntity(sourceEntity);
        }

        // Group relationships by relationship_type
        Map<Object, Set<Map<String, Object>>> groupedRelationships = relationships.stream()
                .collect(Collectors.groupingBy(
                        relationship -> relationship.getRelationshipType().name(),
                        Collectors.mapping(entityRelationshipService::mapRelationship, Collectors.toSet())));

        return ResponseEntity.ok(Map.of("relationships", groupedRelationships));
    }

    /**
     * Validates the request parameters for the entity relationships endpoint.
     *
     * @param sourceEntityId    The ID of the source entity.
     * @param relationshipTypes A set of strings representing relationship types.
     * @return True if the request parameters are valid, false otherwise.
     */
    private boolean validateRequest(Long sourceEntityId, Set<String> relationshipTypes) {
        if (sourceEntityId <= 0) {
            return false;
        }

        // Validate and convert relationshipTypes to RelationshipType enum
        Set<RelationshipType> validRelationshipTypes = validateAndConvertRelationshipTypes(relationshipTypes);
        if (validRelationshipTypes == null) {
            return false;
        }

        return true;
    }

    /**
     * Validates and converts a set of relationship type strings to a set of
     * RelationshipType enum values.
     *
     * @param relationshipTypes A set of strings representing relationship types.
     * @return A set of RelationshipType enum values if validation is successful, or
     *         null if an invalid relationship type is encountered.
     * @throws IllegalArgumentException If the provided set contains an invalid
     *                                  relationship type.
     */

    private Set<RelationshipType> validateAndConvertRelationshipTypes(Set<String> relationshipTypes) {
        if (relationshipTypes == null) {
            return Collections.emptySet();
        }

        try {
            return relationshipTypes.stream()
                    .map(RelationshipType::valueOf)
                    .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid relationship type", e);
            return null; // Invalid relationship type found
        }
    }
}
