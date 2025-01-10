package com.ikservices.oficinamecanica.workorders.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderStatusEnum;

public interface WorkOrderRepository {
	List<WorkOrder> listWorkOrder(SourceCriteriaEnum source, Object criteriaId, 
			WorkOrderStatusEnum status);
	WorkOrder getWorkOrder(WorkOrderId workOrderId);
	WorkOrder saveWorkOrder(WorkOrder workOrder, Long vehicleId);
	WorkOrder updateWorkOrder(WorkOrder workOrder, WorkOrderId workOrderId);
}
