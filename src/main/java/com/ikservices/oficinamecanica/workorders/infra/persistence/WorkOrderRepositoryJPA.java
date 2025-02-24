package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.util.List;

import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;

public interface WorkOrderRepositoryJPA extends JpaRepository<WorkOrderEntity, WorkOrderEntityId>{
	
	@Query("SELECT CASE WHEN MAX(w.id.workOrderId) IS NULL THEN 1 "
			+ "ELSE (MAX(w.id.workOrderId) + 1) END "
			+ "FROM WorkOrderEntity w WHERE w.budget.vehicle.workshopId = :workshopId")
	public Long getNextWorkOrderId(@Param("workshopId") Long workshopId);
	
	@Query("SELECT w FROM WorkOrderEntity w WHERE w.budget.vehicle.workshopId = :workshopId AND w.woStatus = :status")
	public List<WorkOrderEntity> findAllByWorkshop(@Param("workshopId") Long workshopId, 
			@Param("status") WorkOrderStatusEnum status);
	//TODO Improve this query to find a list of work orders that belong to a customer from a workshop and must have a certain status
	@Query("SELECT w FROM WorkOrderEntity w WHERE w.budget.vehicle.customerEntity.id = :customerId AND w.woStatus = :status")
	public List<WorkOrderEntity> findAllByCustomer(@Param("customerId") CustomerEntityId customerId,
			@Param("status") WorkOrderStatusEnum status);
	
	@Query("SELECT w FROM WorkOrderEntity w WHERE w.budget.vehicleId= :vehicleId AND w.woStatus = :status")
	public List<WorkOrderEntity> findAllByVehicle(@Param("vehicleId") Long vehicleId, 
			@Param("status") WorkOrderStatusEnum status);
}
