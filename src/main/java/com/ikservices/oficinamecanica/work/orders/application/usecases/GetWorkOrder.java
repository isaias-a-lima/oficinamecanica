package com.ikservices.oficinamecanica.work.orders.application.usecases;

import com.ikservices.oficinamecanica.work.orders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrder;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderId;

public class GetWorkOrder {
	private WorkOrderRepository repository;
	
	public GetWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrderId workOrderId) {
		return repository.getWorkOrder(workOrderId);
	}
}
