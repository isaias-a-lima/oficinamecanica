package com.ikservices.oficinamecanica.workorders.payments.infra.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.payables.infra.PayableConstant;
import com.ikservices.oficinamecanica.payables.infra.controller.PayableDTO;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.ListPayments;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConstant;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConverter;
import com.ikservices.oficinamecanica.workorders.payments.infra.dto.PaymentDTO;

@RestController
@RequestMapping("payments")
public class PaymentController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PaymentController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private PaymentConverter converter;
	
	ListPayments listPayments;
	
	public PaymentController(ListPayments listPayments) {
		this.listPayments = listPayments;
	}
	
	@GetMapping("list")
	public ResponseEntity<IKResponse<PaymentDTO>> listPayments(@RequestParam(name = "workOrderId") Long workOrderId,
	@RequestParam(name = "budgetId") Long budgetId) {
		try {
			WorkOrderId woId = new WorkOrderId(workOrderId, budgetId);
			
			List<PaymentDTO> paymentDTOList = converter.parseDomainToResponseList(listPayments.execute(woId));
			
			return ResponseEntity.ok(IKResponse.<PaymentDTO>build().body(paymentDTOList));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<PaymentDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<PaymentDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(PaymentConstant.LIST_ERROR_MESSAGE)));
		}
	}
}
