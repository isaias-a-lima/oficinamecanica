package com.ikservices.oficinamecanica.workorders.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.infra.persistence.WorkOrderInstallmentEntity;

public interface WorkOrderRepository {
	List<WorkOrder> getWorkOrderList(SourceCriteriaEnum source, Object criteriaId, WorkOrderStatusEnum status);
	WorkOrder getWorkOrder(WorkOrderId workOrderId);
	WorkOrder saveWorkOrder(WorkOrder workOrder);
	WorkOrder updateWorkOrder(WorkOrder workOrder);
	Boolean finalizeWorkOrder(WorkOrderId workOrderId);
	WorkOrder updatePayments(WorkOrder workOrder);
}
