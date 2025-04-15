package com.ikservices.oficinamecanica.workorders.application.gateways;

import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;

import java.util.List;

public interface WorkOrderRepository {
	List<WorkOrder> getWorkOrderList(SourceCriteriaEnum source, Object criteriaId, WorkOrderStatusEnum status);
	WorkOrder getWorkOrder(WorkOrderId workOrderId);
	WorkOrder saveWorkOrder(WorkOrder workOrder);
	WorkOrder updateWorkOrder(WorkOrder workOrder);
	Boolean finalizeWorkOrder(WorkOrderId workOrderId);
	WorkOrder updatePayments(WorkOrder workOrder);
}
