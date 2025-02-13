package com.ikservices.oficinamecanica.workorders.infra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.controller.WorkOrderRequestDTO;
import com.ikservices.oficinamecanica.workorders.infra.controller.WorkOrderResponseDTO;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
import com.ikservices.oficinamecanica.workorders.installments.infra.WorkOrderInstallmentConverter;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.WorkOrderPartItemConverter;
import com.ikservices.oficinamecanica.workorders.items.services.infra.WorkOrderServiceItemsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderConverter {
	@Autowired
	@Lazy
	private BudgetConverter budgetConverter;
	@Autowired
	@Lazy
	private WorkOrderInstallmentConverter installmentConverter;
	@Autowired
	@Lazy
	private WorkOrderServiceItemsConverter serviceItemsConverter;
	@Autowired
	@Lazy
	private WorkOrderPartItemConverter partItemConverter;
	

	
	public WorkOrder parseWorkOrder(WorkOrderEntity entity) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null Object.");
		}
		
		WorkOrder workOrder = new WorkOrder();
		workOrder.setId(new WorkOrderId(entity.getId().getWorkOrderId(), 
				entity.getId().getBudgetId()));
		
		if(Objects.nonNull(entity.getBudget()) && 
				Objects.nonNull(entity.getBudget().getVehicle())) {
			Budget budget = budgetConverter.parseBudget(entity.getBudget(), true);
			
			Map<Long, Budget> budgetMap = new HashMap<>();
			budgetMap.put(entity.getBudget().getBudgetId(), budget);
			
			Map<Long, Map<Long, Budget>> map = new HashMap<>();
			map.put(entity.getBudget().getVehicleId(), budgetMap);
			
			workOrder.setBudget(map);
		}
		workOrder.setOpeningDate(entity.getOpeningDate());
		workOrder.setKm(entity.getKm());
		workOrder.setWorkOrderStatus(entity.getWoStatus());
		workOrder.setAmount(entity.getAmount());
		workOrder.setPayForm(entity.getPayForm());
		workOrder.setPayQty(entity.getPayQty());
		workOrder.setPaid(entity.getPaid());		
		workOrder.setInstallments(Objects.nonNull(entity.getInstallments()) ? installmentConverter.parseInstallmentList(entity.getInstallments()) : new ArrayList<>());
		workOrder.setServiceItems(Objects.nonNull(entity.getServiceItems()) ? serviceItemsConverter.parseEntityToDomainList(entity.getServiceItems()) : new ArrayList<>());
		workOrder.setPartItems(Objects.nonNull(entity.getPartItems()) ? partItemConverter.parseEntityToDomainList(entity.getPartItems()) : new ArrayList<>());
		return workOrder;
	}
	
	public WorkOrderEntity parseWorkOrderEntity(WorkOrder workOrder) {
		if(Objects.isNull(workOrder)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderEntity entity = new WorkOrderEntity();
		entity.setId(new WorkOrderEntityId(workOrder.getId().getWorkOrderId(), 
				workOrder.getId().getBudgetId()));
		
		Map<Long, Map<Long, Budget>> borderMap = workOrder.getBudget();
		for (Long vehicleId : borderMap.keySet()) {
			Map<Long, Budget> budgetMap = borderMap.get(vehicleId);
			for (Long budgetId : budgetMap.keySet()) {
				entity.setBudget(budgetConverter.parseEntity(budgetMap.get(budgetId), vehicleId, budgetId));
			}
		}
		
		entity.setOpeningDate(workOrder.getOpeningDate());
		entity.setKm(workOrder.getKm());
		entity.setWoStatus(workOrder.getWorkOrderStatus());
		entity.setAmount(workOrder.getAmount());
		entity.setPayForm(workOrder.getPayForm());
		
		entity.setPayQty(workOrder.getPayQty());
		entity.setPaid(workOrder.getPaid());
		entity.setInstallments(Objects.nonNull(workOrder.getInstallments()) ? installmentConverter.parseEntityList(workOrder.getInstallments()) : new ArrayList<>());
		entity.setServiceItems(Objects.nonNull(workOrder.getServiceItems()) ? serviceItemsConverter.parseDomainToEntityList(workOrder.getServiceItems()) : new ArrayList<>());
		entity.setPartItems(Objects.nonNull(workOrder.getPartItems()) ? partItemConverter.parseDomainToEntityList(workOrder.getPartItems()) : new ArrayList<>());
		
		return entity;
	}
	
	public List<WorkOrder> parseWorkOrderList(List<WorkOrderEntity> workOrderEntityList) {
		List<WorkOrder> workOrderList = new ArrayList<>();
		
		if(Objects.nonNull(workOrderEntityList) && !workOrderEntityList.isEmpty()) {
			for (WorkOrderEntity workOrderEntity : workOrderEntityList) {
				workOrderList.add(this.parseWorkOrder(workOrderEntity));
			}
		}
		
		return workOrderList;
	}
	
	public List<WorkOrderEntity> parseWorkOrdeEntityList(List<WorkOrder> workOrderList) {
		List<WorkOrderEntity> workOrderEntityList = new ArrayList<>();
		
		if(Objects.nonNull(workOrderList) && !workOrderList.isEmpty()) {
			for (WorkOrder workOrder : workOrderList) {
				workOrderEntityList.add(this.parseWorkOrderEntity(workOrder));
			}
		}
			
		return workOrderEntityList;
	}
	
	public WorkOrder parseWorkOrder(WorkOrderRequestDTO requestDTO) {
		if(Objects.isNull(requestDTO)) {
			throw new IKException("Null Object");
		}
		
		WorkOrder workOrder = new WorkOrder();
		workOrder.setId(new WorkOrderId(requestDTO.getWorkOrderId(), requestDTO.getBudgetId()));
		
		Map<Long, Budget> budgetMap = new HashMap<>();
		budgetMap.put(requestDTO.getBudgetId(), new Budget());
		
		Map<Long, Map<Long, Budget>> map = new HashMap<>();
		map.put(null, budgetMap);
		
		workOrder.setBudget(map);
		workOrder.setOpeningDate(LocalDate.parse(requestDTO.getOpeningDate()));
		workOrder.setKm(requestDTO.getKm());
		workOrder.setWorkOrderStatus(requestDTO.getWorkOrderStatus());
		workOrder.setAmount(requestDTO.getAmount());
		workOrder.setPayForm(requestDTO.getPayForm());
		workOrder.setPayQty(requestDTO.getPayQty());
		workOrder.setPaid(requestDTO.isPaid());
		workOrder.setInstallments(Objects.nonNull(requestDTO.getInstallments()) ? installmentConverter.parseDTOToDomainList(requestDTO.getInstallments()) : new ArrayList<>());
		workOrder.setServiceItems(Objects.nonNull(requestDTO.getServiceItems()) ? serviceItemsConverter.parseRequestToDomainList(requestDTO.getServiceItems()) : new ArrayList<>());
		workOrder.setPartItems(Objects.nonNull(requestDTO.getPartItems()) ? partItemConverter.parseRequestToDomainList(requestDTO.getPartItems()) : new ArrayList<>());
		return workOrder;
	}
	
	public WorkOrderResponseDTO parseResponseDTO(WorkOrder workOrder) {
		if(Objects.isNull(workOrder)) {
			throw new IKException("Null Object");
		}
		
		WorkOrderResponseDTO responseDTO = new WorkOrderResponseDTO();
		responseDTO.setWorkOrderId(workOrder.getId().getWorkOrderId());
		responseDTO.setBudgetId(workOrder.getId().getBudgetId());
		
		if (Objects.nonNull(workOrder.getBudget()) && !workOrder.getBudget().isEmpty()) {
			Map<Long, Map<Long, Budget>> outSideBudgetMap = workOrder.getBudget();
			
			for (Long vehicleId : outSideBudgetMap.keySet()) {
				Map<Long, Budget> inSideBudgetMap = outSideBudgetMap.get(vehicleId);
				budgetConverter.parseBudgetDTO(inSideBudgetMap, vehicleId,true);
			}			
		}
		
		responseDTO.setOpeningDate(workOrder.getOpeningDate().toString());
		responseDTO.setKm(workOrder.getKm());
		responseDTO.setWOStatus(workOrder.getWorkOrderStatus().ordinal());
		responseDTO.setAmount(NumberUtil.parseStringMoney(workOrder.getAmount()));
		responseDTO.setPayForm(Objects.nonNull(workOrder.getPayForm()) ? workOrder.getPayForm().ordinal() : null);
		responseDTO.setPayQty(workOrder.getPayQty());
		responseDTO.setPaid(Objects.nonNull(workOrder.getPaid()) ? workOrder.getPaid() : false);
		
		responseDTO.setInstallments(Objects.nonNull(workOrder.getInstallments()) ? installmentConverter.parseDomainToDTO(workOrder.getInstallments()) : new ArrayList<>());
		responseDTO.setServiceItems(Objects.nonNull(workOrder.getServiceItems()) ? serviceItemsConverter.parseDomainToResponseList(workOrder.getServiceItems()) : new ArrayList<>());
		responseDTO.setPartItems(Objects.nonNull(workOrder.getPartItems()) ? partItemConverter.parseDomainToResponseList(workOrder.getPartItems()) : new ArrayList<>());
		
		return responseDTO;
	}
	
	public List<WorkOrderResponseDTO> parseResponseDTOList(List<WorkOrder> workOrderList) {
		List<WorkOrderResponseDTO> responseDTOList = new ArrayList<>();
		
		if(Objects.nonNull(workOrderList) && !workOrderList.isEmpty()) {
			for(WorkOrder workOrder : workOrderList) {
				responseDTOList.add(this.parseResponseDTO(workOrder));
			}
		}
		
		return responseDTOList;
	}

	public WorkOrderRequestDTO createRequest(WorkOrderResponseDTO response) {
		if (Objects.isNull(response)) {
			throw new IKException("Null object.");
		}
		WorkOrderRequestDTO request = new WorkOrderRequestDTO();
		request.setWorkOrderId(response.getWorkOrderId());
		request.setBudgetId(response.getBudgetId());
		request.setOpeningDate(response.getOpeningDate());
		request.setKm(response.getKm());
		request.setWorkOrderStatus(WorkOrderStatusEnum.findByIndex(response.getWOStatus()));
		request.setAmount(NumberUtil.parseBigDecimal(response.getAmount()));
		request.setPayForm(Objects.nonNull(response.getPayForm()) ? PayFormEnum.findByIndex(response.getPayForm()) : null);
		request.setPayQty(response.getPayQty());
		request.setPaid(response.isPaid());
		request.setInstallments(response.getInstallments());
		request.setServiceItems(serviceItemsConverter.createRequestList(response.getServiceItems()));
		request.setPartItems(partItemConverter.createRequestList(response.getPartItems()));
		return request;
	}
}
