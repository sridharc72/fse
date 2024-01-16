package com.pulsara.fse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.repository.EntityRepository;

import java.util.List;

@Service
public class EntityService {

    private final EntityRepository entityRepository;

    @Autowired
    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public List<EntityModel> getAllEntities() {
        return entityRepository.findAll();
    }

    public EntityModel getEntityById(Long entityId) {
        return entityRepository.findById(entityId).orElse(null);
    }
}

