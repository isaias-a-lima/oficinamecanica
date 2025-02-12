package com.ikservices.oficinamecanica.workorders.items.services.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderServiceItemRepositoryJPA extends JpaRepository<WorkOrderServiceItemEntity, WorkOrderServiceItemEntityId>{
	
}
