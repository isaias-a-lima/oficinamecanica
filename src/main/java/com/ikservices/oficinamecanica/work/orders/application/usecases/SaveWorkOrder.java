package com.ikservices.oficinamecanica.work.orders.application.usecases;

import com.ikservices.oficinamecanica.work.orders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrder;

public class SaveWorkOrder {
	private final WorkOrderRepository repository;
	
	public SaveWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrder workOrder, Long vehicleId) {
		return repository.saveWorkOrder(workOrder, vehicleId);
	}
}
