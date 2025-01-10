package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;

public class UpdateWorkOrder {
	private final WorkOrderRepository repository;
	
	public UpdateWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrder workOrder, WorkOrderId workOrderId) {
		return repository.updateWorkOrder(workOrder, workOrderId);
	}
}
