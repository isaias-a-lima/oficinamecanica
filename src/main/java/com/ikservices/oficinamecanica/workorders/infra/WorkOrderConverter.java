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
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.infra.controller.WorkOrderRequestDTO;
import com.ikservices.oficinamecanica.workorders.infra.controller.WorkOrderResponseDTO;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntityId;
import com.ikservices.oficinamecanica.workorders.installments.infra.WorkOrderInstallmentConverter;

public class WorkOrderConverter {
	
	private final BudgetConverter budgetConverter;
	private final WorkOrderInstallmentConverter installmentConverter;
	
	public WorkOrderConverter(BudgetConverter budgetConverter, WorkOrderInstallmentConverter installmentConverter) {
		this.budgetConverter = budgetConverter;
		this.installmentConverter = installmentConverter;
	}
	
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
		workOrder.setWorkOrderStatus(entity.getWostatus());
		workOrder.setAmount(entity.getAmount());
		workOrder.setPayForm(entity.getPayForm());
		workOrder.setPayQty(entity.getPayQty());
		workOrder.setPaid(entity.getPaid());		
		workOrder.setInstallments(Objects.nonNull(entity.getInstallments()) ? installmentConverter.parseInstallmentList(entity.getInstallments()) : new ArrayList<>());		
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
		entity.setWostatus(workOrder.getWorkOrderStatus());
		entity.setAmount(workOrder.getAmount());
		entity.setPayForm(workOrder.getPayForm());
		
		entity.setPayQty(workOrder.getPayQty());
		entity.setPaid(workOrder.getPaid()); 
		
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
		workOrder.setWorkOrderStatus(requestDTO.getWOStatus());
		workOrder.setAmount(requestDTO.getAmount());
		workOrder.setPayForm(requestDTO.getPayForm());
		workOrder.setPayQty(requestDTO.getPayQty());
		workOrder.setPaid(requestDTO.isPaid());
		
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
		responseDTO.setAmount(workOrder.getAmount());
		responseDTO.setPayForm(workOrder.getPayForm().ordinal());
		responseDTO.setPayQty(workOrder.getPayQty());
		responseDTO.setPaid(workOrder.getPaid());
		
		responseDTO.setInstallments(Objects.nonNull(workOrder.getInstallments()) ? installmentConverter.parseDomainToDTO(workOrder.getInstallments()) : new ArrayList<>());
		
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
}
