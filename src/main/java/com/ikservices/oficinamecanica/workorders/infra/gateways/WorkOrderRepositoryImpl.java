package com.ikservices.oficinamecanica.workorders.infra.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderRepositoryJPA;

public class WorkOrderRepositoryImpl implements WorkOrderRepository {
	
	private final WorkOrderConverter converter;
	private final WorkOrderRepositoryJPA repository;
	
	public WorkOrderRepositoryImpl(WorkOrderConverter converter, WorkOrderRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}

	@Override
	public List<WorkOrder> getWorkOrderList(SourceCriteriaEnum source, Object criteriaId, WorkOrderStatusEnum status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkOrder getWorkOrder(WorkOrderId workOrderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkOrder saveWorkOrder(WorkOrder workOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkOrder updateWorkOrder(WorkOrder workOrder) {
		// TODO Auto-generated method stub
		return null;
	}
}
