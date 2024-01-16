package com.pulsara.fse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pulsara.fse.models.EntityModel;

@Repository
public interface EntityRepository extends JpaRepository<EntityModel, Long> {

}
