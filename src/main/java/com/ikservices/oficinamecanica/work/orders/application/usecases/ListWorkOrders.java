package com.ikservices.oficinamecanica.work.orders.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.work.orders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.work.orders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrder;
import com.ikservices.oficinamecanica.work.orders.domain.WorkOrderStatusEnum;

public class ListWorkOrders {
	private final WorkOrderRepository repository;
	
	public ListWorkOrders(WorkOrderRepository repository) {
		this.repository = repository;
	}
	
	public List<WorkOrder> execute(SourceCriteriaEnum source, Object criteriaId,
			WorkOrderStatusEnum status) {
		return repository.listWorkOrder(source, criteriaId, status);
	}
}
