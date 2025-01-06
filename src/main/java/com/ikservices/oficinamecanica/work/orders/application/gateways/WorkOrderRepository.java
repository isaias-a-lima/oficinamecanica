package com.ikservices.oficinamecanica.work.orders.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.work.orders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrder;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderStatusEnum;

public interface WorkOrderRepository {
	List<WorkOrder> listWorkOrder(SourceCriteriaEnum source, Object criteriaId, 
			WorkOrderStatusEnum status);
	WorkOrder getWorkOrder(WorkOrderId workOrderId);
	WorkOrder saveWorkOrder(WorkOrder workOrder, Long vehicleId);
	WorkOrder updateWorkOrder(WorkOrder workOrder, WorkOrderId workOrderId);
}
