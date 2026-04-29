package com.ikservices.oficinamecanica.inventory.infra.controller;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.DateUtil;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.inventory.MovementConverter;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetBalanceByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetLastFinalBalanceDateByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetLastFinalBalanceValueByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetMovementQuantityByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.ListMovementByPartAndType;
import com.ikservices.oficinamecanica.inventory.application.usecases.SaveMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.UpdateMovement;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;
import com.ikservices.oficinamecanica.inventory.infra.constants.MovementConstant;
import com.ikservices.oficinamecanica.workorders.payments.infra.controller.PaymentController;

@RestController
@RequestMapping("movements")
public class MovementController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PaymentController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private MovementConverter converter;
	
	ListMovementByPartAndType listMovementByPartAndType;
	GetMovement getMovement;
	SaveMovement saveMovement;
	UpdateMovement updateMovement;
	GetBalanceByPart getBalanceByPart;
	
	public MovementController(ListMovementByPartAndType listMovementByPartAndType, GetMovement getMovement,
			SaveMovement saveMovement, UpdateMovement updateMovement, GetBalanceByPart getBalanceByPart) {
		this.listMovementByPartAndType = listMovementByPartAndType;
		this.getMovement = getMovement;
		this.saveMovement = saveMovement;
		this.updateMovement = updateMovement;
		this.getBalanceByPart = getBalanceByPart;
	}
	
	@GetMapping("list")
	public ResponseEntity<IKResponse<MovementDTO>> ListMovementByPartAndType(
			@RequestParam(name = "partId") Integer partId,
			@RequestParam(name = "workshopId") Long workshopId,
			@RequestParam(name = "startDate") String startDate, 
			@RequestParam(name="endDate") String endDate,
			@RequestParam(name="movementType") Integer movementType) {
		try {
			List<MovementDTO> movementDTOList = converter.parseDomainToResponseList(listMovementByPartAndType.execute(partId, workshopId,
					DateUtil.parseToLocalDate(startDate), DateUtil.parseToLocalDate(endDate),
					MovementTypeEnum.findByIndex(movementType)));
			
			return ResponseEntity.ok(IKResponse.<MovementDTO>build().body(movementDTOList));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<MovementDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<MovementDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(MovementConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("get/{workshopId}/{inventoryId}")
	public ResponseEntity<IKResponse<MovementDTO>> getMovement(@PathVariable Long workshopId, @PathVariable Long inventoryId) {
		try {
			MovementDTO movementDTO = converter.parseDomainToResponse(getMovement.
					execute(workshopId, inventoryId));
			return ResponseEntity.ok(IKResponse.<MovementDTO>build().body(movementDTO));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<MovementDTO>build().addMessage(ike.getIkMessage().getCode(), 
							IKMessageType.getByCode(ike.getIkMessage().getType()), 
							ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<MovementDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(MovementConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("get/balance/{partId}/{workshopId}")
	public ResponseEntity<IKResponse<Integer>> getBalanceByPart(@PathVariable Integer partId, 
			@PathVariable Long workshopId) {
		try {
			Integer balance = getBalanceByPart.execute(partId, workshopId);
			
			return ResponseEntity.ok(IKResponse.<Integer>build().body(balance));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<Integer>build().addMessage(ike.getIkMessage().getCode(), 
							IKMessageType.getByCode(ike.getIkMessage().getType()), 
							ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<Integer>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(MovementConstant.GET_ERROR_MESSAGE)));
		}
	}
}
