package com.ikservices.oficinamecanica.workorders.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderInstallmentJPA extends JpaRepository<WorkOrderInstallmentEntity, WorkOrderInstallmentEntityId>{
	
}
