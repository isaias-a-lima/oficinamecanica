package com.ikservices.oficinamecanica.inventory.infra.controller;

import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.DateUtil;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.inventory.MovementConverter;
import com.ikservices.oficinamecanica.inventory.application.usecases.ListMovementByPartAndType;
import com.ikservices.oficinamecanica.inventory.application.usecases.SaveMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.UpdateMovement;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.infra.constants.MovementConstant;
import com.ikservices.oficinamecanica.workorders.payments.infra.controller.PaymentController;

public class MovementController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PaymentController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private MovementConverter converter;
	
	ListMovementByPartAndType listMovementByPartAndType;
	SaveMovement saveMovement;
	UpdateMovement updateMovement;
	
	public MovementController(ListMovementByPartAndType listMovementByPartAndType, SaveMovement saveMovement, 
			UpdateMovement updateMovement) {
		this.listMovementByPartAndType = listMovementByPartAndType;
		this.saveMovement = saveMovement;
		this.updateMovement = updateMovement;
	}
	
	@GetMapping("list")
	public ResponseEntity<IKResponse<MovementDTO>> ListMovementByPartAndType(InventoryMovement movement, 
			@RequestParam(name = "startDate") String startDate, 
			@RequestParam(name="endDate") String endDate) {
		try {
			List<MovementDTO> movementDTOList = converter.parseDomainToResponseList(listMovementByPartAndType.execute(movement.getPartId(), movement.getId().getWorkshopId(),
					DateUtil.parseToLocalDate(startDate), DateUtil.parseToLocalDate(endDate),
					movement.getMovementType()));
			
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
}
