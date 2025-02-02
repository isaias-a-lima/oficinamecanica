package com.ikservices.oficinamecanica.workorders.infra.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
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
		List<WorkOrderEntity> entityList = null;
		
		switch(source) {
			case WORKSHOP:
				entityList = repository.findAllByWorkshop(new Long(criteriaId.toString()), status);
				return converter.parseWorkOrderList(entityList);
			case CUSTOMER:
				entityList = repository.findAllByCustomer((String)criteriaId, status);
				return converter.parseWorkOrderList(entityList);
			case VEHICLE:
				entityList = repository.findAllByVehicle((Long)criteriaId, status);
				return converter.parseWorkOrderList(entityList);
			default:
				throw new IKException("Not found");
		}
	}

	@Override
	public WorkOrder getWorkOrder(WorkOrderId workOrderId) {
		WorkOrderEntity workOrderEntity = repository.getById(new WorkOrderEntityId(workOrderId.getWorkOrderId(), 
				workOrderId.getBudgetId()));
		
		return converter.parseWorkOrder(workOrderEntity);
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
