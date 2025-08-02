package com.ikservices.oficinamecanica.workorders.payments.infra.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;

public interface PaymentRepositoryJPA extends JpaRepository<PaymentEntity, PaymentEntityId> {
	@Query("SELECT p FROM PaymentEntity p WHERE p.workOrder.id = :workOrderEntityId")
	public List<PaymentEntity> findAllByWorkOrderId(@Param("workOrderEntityId") WorkOrderEntityId workOrderEntityId);

	@Query("SELECT p FROM PaymentEntity p WHERE "
			+ "p.workOrder.budget.vehicle.workshopId = :workshopId "
			+ "AND p.dueDate < curdate() "
			+ "AND p.payDate IS NULL")
	public List<PaymentEntity> listOverduePaymentsByWorkshopId(@Param("workshopId") Long workshopId);
	
	@Query("SELECT p FROM PaymentEntity p WHERE "
			+ "p.workOrder.budget.vehicle.workshopId = :workshopId "
			+ "AND ((:paymentState = 'PAID' AND p.payDate IS NOT NULL) OR "
			+ "(:paymentState = 'UNPAID' AND p.payDate IS NULL) OR "
			+ "(:paymentState = 'NONE')) "
			+ "AND p.dueDate BETWEEN :startDate AND :endDate "
			+ "ORDER BY p.dueDate, p.id.workOrderId, p.id.number")
	public List<PaymentEntity> listPaymentsByDuePeriod(@Param("workshopId") Long workshopId, 
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
			@Param("paymentState") String paymentState);
}
