package com.pulsara.fse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsara.fse.models.EntityModel;
import com.pulsara.fse.service.EntityService;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
public class EntityController {

    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public List<EntityModel> getAllEntities() {
        return entityService.getAllEntities();
    }
}
