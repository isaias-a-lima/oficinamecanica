package com.ikservices.oficinamecanica.workorders.infra.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.budgets.application.BudgetBusinessConstant;
import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.budgets.infra.controller.BudgetDTO;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.usecases.GetWorkOrder;
import com.ikservices.oficinamecanica.workorders.application.usecases.ListWorkOrders;
import com.ikservices.oficinamecanica.workorders.application.usecases.SaveWorkOrder;
import com.ikservices.oficinamecanica.workorders.application.usecases.UpdateWorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.constants.WorkOrderConstant;

@RestController
@RequestMapping("workorders")
public class WorkOrderController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(WorkOrderController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private WorkOrderConverter converter;
	
	private final ListWorkOrders listWorkOrders;
	private final GetWorkOrder getWorkOrder;
	private final SaveWorkOrder saveWorkOrder;
	private final UpdateWorkOrder updateWorkOrder;
	
	public WorkOrderController(ListWorkOrders listWorkOrders, GetWorkOrder getWorkOrder,
			SaveWorkOrder saveWorkOrder, UpdateWorkOrder updateWorkOrder) {
		this.listWorkOrders = listWorkOrders;
		this.getWorkOrder = getWorkOrder;
		this.saveWorkOrder = saveWorkOrder;
		this.updateWorkOrder = updateWorkOrder;
	}
	
	@GetMapping("list/{source}/{criteriaId}")
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> listWorkOrders(@PathVariable SourceCriteriaEnum source, 
			@PathVariable Object criteriaId, @RequestParam(name = "status") WorkOrderStatusEnum status) {
		
		try {
			List<WorkOrderResponseDTO> workOrderDTOList = converter.parseResponseDTOList(listWorkOrders.execute(source, criteriaId, status));
			
			return ResponseEntity.ok(IKResponse.<WorkOrderResponseDTO>build().body(workOrderDTOList));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<WorkOrderResponseDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<WorkOrderResponseDTO>build().
							addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
									environment.getProperty(WorkOrderConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("get/")
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> getWorkOrder(@RequestParam(name = "workOrderId") Long workOrderId, 
			@RequestParam(name = "budgetId") Long budgetId) {
		
		try {
			WorkOrderId woId = new WorkOrderId(workOrderId, budgetId);
			WorkOrderResponseDTO workOrderResponseDTO = converter.parseResponseDTO(getWorkOrder.execute(woId));
			
			return ResponseEntity.ok(IKResponse.<WorkOrderResponseDTO>build().body(workOrderResponseDTO));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<WorkOrderResponseDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<WorkOrderResponseDTO>build().
							addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
									environment.getProperty(WorkOrderConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> updateWorkOrder(@RequestBody WorkOrderRequestDTO requestDTO) {
		try {
			WorkOrder workOrder = converter.parseWorkOrder(requestDTO);
			WorkOrderResponseDTO responseDTO = converter.parseResponseDTO(updateWorkOrder.execute(workOrder));
			
			
			return ResponseEntity.ok(IKResponse.<WorkOrderResponseDTO>build().body(responseDTO).
					addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, 
							WorkOrderConstant.UPDATE_SUCCESS_MESSAGE));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<WorkOrderResponseDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<WorkOrderResponseDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(WorkOrderConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
}
