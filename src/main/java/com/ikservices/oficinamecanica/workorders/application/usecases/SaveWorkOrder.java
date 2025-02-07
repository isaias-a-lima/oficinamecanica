package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;

public class SaveWorkOrder {
	private final WorkOrderRepository repository;
	
	public SaveWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrder workOrder) {
		return repository.saveWorkOrder(workOrder);
	}
}
