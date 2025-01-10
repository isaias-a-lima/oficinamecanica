package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;

public class GetWorkOrder {
	private WorkOrderRepository repository;
	
	public GetWorkOrder(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public WorkOrder execute(WorkOrderId workOrderId) {
		return repository.getWorkOrder(workOrderId);
	}
}
