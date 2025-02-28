package com.ikservices.oficinamecanica.workorders.infra.gateways;

import java.util.List;
import java.util.Optional;

import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.workorders.application.usecases.business.WorkOrderEndingValidations;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.installments.infra.WorkOrderInstallmentConverter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.constants.WorkOrderConstant;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderRepositoryJPA;

public class WorkOrderRepositoryImpl implements WorkOrderRepository {

	private static final Logger LOGGER = IKLoggerUtil.getLogger(WorkOrderRepositoryImpl.class);
	
	private final WorkOrderConverter converter;
	private final WorkOrderRepositoryJPA repository;
	@Autowired
	private  WorkOrderInstallmentConverter installmentConverter;
	
	@Autowired
	private Environment environment;
	
	public WorkOrderRepositoryImpl(WorkOrderConverter converter, WorkOrderRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}

	@Override
	public List<WorkOrder> getWorkOrderList(SourceCriteriaEnum source, Object criteriaId, WorkOrderStatusEnum status) {
		List<WorkOrderEntity> entityList = null;
		
		switch(source) {
			case WORKSHOP:
				entityList = repository.findAllByWorkshop(Long.valueOf(criteriaId.toString()), status);
				return converter.parseWorkOrderList(entityList);
			case CUSTOMER:
				entityList = repository.findAllByCustomer((CustomerEntityId) criteriaId, status);
				return converter.parseWorkOrderList(entityList);
			case VEHICLE:
				entityList = repository.findAllByVehicle(Long.valueOf(criteriaId.toString()), status);
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
		WorkOrderEntity saved = null;
		try {
			saved = repository.save(converter.parseWorkOrderEntity(workOrder));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), "Erro ao tentar salvar."));
		}
		return converter.parseWorkOrder(saved);
	}

	@Override
	public WorkOrder updateWorkOrder(WorkOrder workOrder) {
		Optional<WorkOrderEntity> optional = repository.findById(new WorkOrderEntityId(workOrder.getId().getWorkOrderId(), 
						workOrder.getId().getBudgetId()));			
		
		if(optional.isPresent()) {
			try {
				WorkOrderEntity workOrderEntity = optional.get();
				
				workOrderEntity.update(converter.parseWorkOrderEntity(workOrder));
				
				return converter.parseWorkOrder(workOrderEntity);
			}catch(Exception e) {
				throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, 
						IKMessageType.ERROR.getCode(), environment.getProperty(WorkOrderConstant.UPDATE_ERROR_MESSAGE)));
			}
		}
		
		throw new IKException(new IKMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.WARNING.getCode(), environment.getProperty(WorkOrderConstant.GET_NOT_FOUND_MESSAGE)));
	}

	@Override
	public Boolean finalizeWorkOrder(WorkOrderId workOrderId) {

		boolean result = false;

		try {
			Optional<WorkOrderEntity> optional = repository.findById(new WorkOrderEntityId(workOrderId.getWorkOrderId(), workOrderId.getBudgetId()));

			if (optional.isPresent()) {
				WorkOrderEntity workOrderEntity = optional.get();

				List<WorkOrderInstallment> workOrderInstallments = WorkOrderEndingValidations.defineInstallments(converter.parseWorkOrder(workOrderEntity));

				workOrderEntity.addInstallments(installmentConverter.parseEntityList(workOrderInstallments));

				workOrderEntity.setWoStatus(WorkOrderStatusEnum.DONE);

				result = true;

			} else {
				throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), environment.getProperty(WorkOrderConstant.GET_NOT_FOUND_MESSAGE)));
			}
		} catch (IKException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR.getCode(), environment.getProperty(WorkOrderConstant.OPERATION_ERROR_MESSAGE)));
		}

		return result;
	}

}
