package com.ikservices.oficinamecanica.workorders.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderStatusEnum;

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
