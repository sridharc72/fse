package com.pulsara.fse.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.models.EntityRelationshipModel;
import com.pulsara.fse.models.RelationshipType;

/**
 * Repository interface for managing entity relationships.
 * This repository provides methods to interact with the entity relationships
 * stored in the database.
 *
 * @see JpaRepository
 * @since 1.0
 */
public interface EntityRelationshipRepository extends JpaRepository<EntityRelationshipModel, Long> {

        /**
         * Finds entity relationships based on the source entity.
         *
         * @param sourceEntity The source entity for which relationships are to be
         *                     retrieved.
         * @return A list of entity relationships associated with the provided source
         *         entity.
         */
        List<EntityRelationshipModel> findBySourceEntity(EntityModel sourceEntity);

        /**
         * Finds entity relationships based on the relationship type.
         *
         * @param relationshipType The relationship type for which relationships are to
         *                         be retrieved.
         * @return A list of entity relationships associated with the provided
         *         relationship type.
         */
        List<EntityRelationshipModel> findByRelationshipType(RelationshipType relationshipType);

        /**
         * Finds entity relationships based on the source entity, destination entity,
         * and relationship type.
         *
         * @param sourceEntity      The source entity for which relationships are to be
         *                          retrieved.
         * @param destinationEntity The destination entity for which relationships are
         *                          to be retrieved.
         * @param relationshipType  The relationship type for which relationships are to
         *                          be retrieved.
         * @return A list of entity relationships associated with the provided source
         *         entity, destination entity, and relationship type.
         */
        List<EntityRelationshipModel> findBySourceEntityAndDestinationEntityAndRelationshipType(
                        EntityModel sourceEntity, EntityModel destinationEntity, RelationshipType relationshipType);

        /**
         * Finds entity relationships based on the source entity and a collection of
         * relationship types.
         *
         * @param sourceEntity      The source entity for which relationships are to be
         *                          retrieved.
         * @param relationshipTypes A collection of relationship types for which
         *                          relationships are to be retrieved.
         * @return A list of entity relationships associated with the provided source
         *         entity and relationship types.
         */
        @Query("SELECT er FROM EntityRelationshipModel er WHERE er.sourceEntity = :sourceEntity AND er.relationshipType IN :relationshipTypes")
        List<EntityRelationshipModel> findBySourceEntityAndRelationshipTypes(
                        @Param("sourceEntity") EntityModel sourceEntity,
                        @Param("relationshipTypes") Set<RelationshipType> relationshipTypes);

}
