package com.ikservices.oficinamecanica.workorders.infra.controller;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.workorders.application.SourceCriteriaEnum;
import com.ikservices.oficinamecanica.workorders.application.usecases.*;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.constants.WorkOrderConstant;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
	private final FinalizeWorkOrder finalizeWorkOrder;
	private final UpdateWorkOrder updateWorkOrder;
	private final CreateWorkOrderPDF createPDF;

	private final UpdatePayments updatePayments;
	
	public WorkOrderController(ListWorkOrders listWorkOrders, GetWorkOrder getWorkOrder, FinalizeWorkOrder finalizeWorkOrder, UpdateWorkOrder updateWorkOrder, CreateWorkOrderPDF createPDF, UpdatePayments updatePayments) {
		this.listWorkOrders = listWorkOrders;
		this.getWorkOrder = getWorkOrder;
		this.finalizeWorkOrder = finalizeWorkOrder;
		this.updateWorkOrder = updateWorkOrder;
		this.createPDF = createPDF;
        this.updatePayments = updatePayments;
    }
	
	@GetMapping("list/{source}/{criteriaId}/{workshopId}")
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> listWorkOrders(@PathVariable SourceCriteriaEnum source, 
			@PathVariable Object criteriaId, @PathVariable Long workshopId , @RequestParam(name = "status") WorkOrderStatusEnum status) {
		
		try {

			Object criteriaAux;

			if (SourceCriteriaEnum.CUSTOMER.equals(source)) {
				criteriaAux = new CustomerEntityId(workshopId, (String) criteriaId);
			} else {
				criteriaAux = criteriaId;
			}

			List<WorkOrderResponseDTO> workOrderDTOList = converter.parseResponseDTOList(listWorkOrders.execute(source, criteriaAux, status));
			
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
	
	@GetMapping("get")
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> getWorkOrder(@RequestParam(name = "workOrderId") Long workOrderId, 
			@RequestParam(name = "budgetId") Long budgetId) {
		
		try {
			WorkOrderId woId = new WorkOrderId(workOrderId, budgetId);
			WorkOrderResponseDTO workOrderResponseDTO = converter.parseResponseDTO(getWorkOrder.execute(woId));
			LOGGER.info("REQUEST JSON: " + IKLoggerUtil.parseJSON(converter.createRequest(workOrderResponseDTO)));
			
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
			
			return ResponseEntity.ok(IKResponse.<WorkOrderResponseDTO>build().body(responseDTO).addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(WorkOrderConstant.UPDATE_SUCCESS_MESSAGE)));
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

	@Transactional
	@PutMapping("finalize")
	public ResponseEntity<IKResponse<Boolean>> completeWorkOrder(@RequestBody WorkOrderEndingDTO dto) {

		try {
			Boolean result = finalizeWorkOrder.execute(new WorkOrderId(dto.getWorkOrderId(), dto.getBudgetId()));

			return ResponseEntity.ok(IKResponse.<Boolean>build().body(result).addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(WorkOrderConstant.FINALIZE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<Boolean>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<Boolean>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(WorkOrderConstant.FINALIZE_ERROR_MESSAGE)));
		}
	}

	@GetMapping("workorder/pdf/{workOrderId}/{budgetId}")
	public ResponseEntity<byte[]> createBudgetPDF(@PathVariable Long workOrderId, @PathVariable Long budgetId, HttpServletResponse response) {
		try {
			WorkOrder execute = getWorkOrder.execute(new WorkOrderId(workOrderId, budgetId));

			byte[] file = createPDF.execute(execute);

			String fileName = createPDF.getFormattedPdfName();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", fileName);

			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(file);

		} catch (EntityNotFoundException e) {
			LOGGER.info("workOrderId: " + workOrderId);
			LOGGER.error(environment.getProperty(WorkOrderConstant.GET_NOT_FOUND_MESSAGE));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Transactional
	@PutMapping("payments")
	public ResponseEntity<IKResponse<WorkOrderResponseDTO>> updatePayments(@RequestBody WorkOrderRequestDTO requestDTO) {

		WorkOrder workOrder = null;

		try {
			LOGGER.info(IKLoggerUtil.parseJSON(requestDTO));

			workOrder = converter.parseWorkOrder(requestDTO);

			WorkOrderResponseDTO result = converter.parseResponseDTO(updatePayments.execute(workOrder));

			return ResponseEntity.ok(IKResponse.<WorkOrderResponseDTO>build().body(result).addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(WorkOrderConstant.UPDATE_SUCCESS_MESSAGE)));
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
