package com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderPartItemRepositoryJPA extends JpaRepository<WorkOrderPartItemEntity, WorkOrderPartItemEntityId> {
}
