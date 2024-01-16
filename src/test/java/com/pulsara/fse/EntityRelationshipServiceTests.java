package com.pulsara.fse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Set;

import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.models.EntityRelationshipModel;
import com.pulsara.fse.models.Patient;
import com.pulsara.fse.models.RelationshipType;
import com.pulsara.fse.repository.EntityRelationshipRepository;
import com.pulsara.fse.service.EntityRelationshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityRelationshipServiceTests {

    private EntityRelationshipRepository repository;
    private EntityRelationshipService service;

    @BeforeEach
    void setUp() {
        repository = mock(EntityRelationshipRepository.class);
        service = new EntityRelationshipService(repository);
    }

    @Test
    void testGetRelationshipsBySourceEntity() {
        // Setup
        EntityModel sourceEntity = new EntityModel(); // Create a sample entity
        when(repository.findBySourceEntity(eq(sourceEntity))).thenReturn(Collections.emptyList());

        // Test
        service.getRelationshipsBySourceEntity(sourceEntity);

        // Verify
        verify(repository, times(1)).findBySourceEntity(eq(sourceEntity));
    }

    @Test
    void testGetPatientsBySourceAndDestinationAndRelationshipType() {
        // Setup
        EntityModel sourceEntity = new EntityModel(); // Create a sample source entity
        EntityModel destinationEntity = new EntityModel(); // Create a sample destination entity
        RelationshipType relationshipType = RelationshipType.TRANSPORT; // Set a relationship type

        // Mock relationships
        EntityRelationshipModel relationship = new EntityRelationshipModel();
        relationship.setSourceEntity(sourceEntity);
        relationship.setDestinationEntity(destinationEntity);
        relationship.setRelationshipType(relationshipType);
        when(repository.findBySourceEntityAndDestinationEntityAndRelationshipType(
                eq(sourceEntity), eq(destinationEntity), eq(relationshipType)))
                .thenReturn(Collections.singletonList(relationship));

        // Test
        service.getPatientsBySourceAndDestinationAndRelationshipType(sourceEntity, destinationEntity, relationshipType);

        // Verify
        verify(repository, times(1))
                .findBySourceEntityAndDestinationEntityAndRelationshipType(
                        eq(sourceEntity), eq(destinationEntity), eq(relationshipType));
    }
}
