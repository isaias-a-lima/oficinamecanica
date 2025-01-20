package com.ikservices.oficinamecanica.workorders.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkOrderRepositoryJPA extends JpaRepository<WorkOrderEntity, WorkOrderEntityId>{
	
	@Query("SELECT CASE WHEN MAX(w.workOrderEntityId.workOrderId) IS NULL THEN 1 "
			+ "ELSE (MAX(w.workOrderEntityId.workOrderId) + 1) END "
			+ "FROM WorkOrderEntity w WHERE w.budget.vehicle.workshopId = :workshopId")
	public Long getNextWorkOrderId(@Param("workshopId") Long workshopId);
}
