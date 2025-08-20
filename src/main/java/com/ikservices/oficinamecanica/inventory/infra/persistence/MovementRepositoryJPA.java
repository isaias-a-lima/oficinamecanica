package com.ikservices.oficinamecanica.inventory.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepositoryJPA extends JpaRepository<MovementEntity, MovementEntityId>{

}
