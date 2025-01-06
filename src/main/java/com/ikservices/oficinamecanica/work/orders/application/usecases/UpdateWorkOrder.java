package com.ikservices.oficinamecanica.work.orders.application.usecases;

import com.ikservices.oficinamecanica.work.orders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrder;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderId;

public class UpdateWorkOrder {
	private final WorkOrderRepository repository;
	
	public UpdateWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrder workOrder, WorkOrderId workOrderId) {
		return repository.updateWorkOrder(workOrder, workOrderId);
	}
}
