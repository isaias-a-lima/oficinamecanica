package com.ikservices.oficinamecanica.payables.infra.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.categories.infra.constants.CategoryConstant;
import com.ikservices.oficinamecanica.categories.infra.controller.CategoryDTO;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.payables.application.usecases.GetNextPayableId;
import com.ikservices.oficinamecanica.payables.application.usecases.GetPayable;
import com.ikservices.oficinamecanica.payables.application.usecases.ListPayable;
import com.ikservices.oficinamecanica.payables.application.usecases.SavePayable;
import com.ikservices.oficinamecanica.payables.application.usecases.UpdatePayable;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;
import com.ikservices.oficinamecanica.payables.infra.PayableConstant;
import com.ikservices.oficinamecanica.payables.infra.PayableConverter;

@RestController
@RequestMapping("payables")
public class PayableController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PayableController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private PayableConverter converter;
	
	private ListPayable listPayable;
	private GetPayable getPayable;
	private SavePayable savePayable;
	private GetNextPayableId getNextPayableId;
	private UpdatePayable updatePayable;
	
	public PayableController(ListPayable listPayable, GetPayable getPayable,
			SavePayable savePayable, GetNextPayableId getNextPayableId, 
			UpdatePayable updatePayable) {
		this.listPayable = listPayable;
		this.getPayable = getPayable;
		this.savePayable = savePayable;
		this.getNextPayableId = getNextPayableId;
		this.updatePayable = updatePayable;
	}
	
	@GetMapping("list/{workshopId}")
	public ResponseEntity<IKResponse<PayableDTO>> listPayable(@PathVariable Long workshopId) {
		try {
			List<PayableDTO> payableDTOList = converter.parseDomainToResponseList(listPayable.execute(workshopId));
			
			return ResponseEntity.ok(IKResponse.<PayableDTO>build().body(payableDTOList));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<PayableDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<PayableDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(PayableConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("get/{payableId}/{workshopId}")
	public ResponseEntity<IKResponse<PayableDTO>> getPayable(@PathVariable Integer payableId, 
			@PathVariable Long workshopId) {
		try {
			PayableDTO payableDTO = converter.
					parseDomainToResponse(getPayable.execute(new PayableId(payableId, workshopId)));
			
			return ResponseEntity.ok(IKResponse.<PayableDTO>build().body(payableDTO));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
					body(IKResponse.<PayableDTO>build().addMessage(ike.getIkMessage().getCode(),
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
					body(IKResponse.<PayableDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, 
							IKMessageType.ERROR, environment.getProperty(PayableConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKResponse<PayableDTO>> savePayable(@RequestBody PayableDTO payableDTO, 
			UriComponentsBuilder uriBuilder) {
		try {
			Payable payable = null;
			
			Integer nextId = getNextPayableId.execute(payableDTO.getWorkshopId());
			payableDTO.setId(nextId);
			
			payable = savePayable.execute(converter.parseRequestToDomain(payableDTO));
			URI uri = uriBuilder.path("payables/{payableId}/{workshopId}").
					buildAndExpand(payable.getId().getPayableId(), payable.getId().getWorkshopId()).
					toUri();
			
			return ResponseEntity.created(uri).body(IKResponse.<PayableDTO>build().
					body(converter.parseDomainToResponse(payable)).addMessage(Constants.DEFAULT_SUCCESS_CODE, 
							IKMessageType.SUCCESS, environment.getProperty(PayableConstant.SAVE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<PayableDTO>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<PayableDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
							environment.getProperty(PayableConstant.SAVE_ERROR_MESSAGE)));
        }
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<IKResponse<PayableDTO>> savePayable(@RequestBody PayableDTO payableDTO) {
		try {
			Payable payable = updatePayable.execute(converter.parseRequestToDomain(payableDTO));
			return ResponseEntity.ok(IKResponse.<PayableDTO>build().body(converter.parseDomainToResponse(payable)).
					addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, 
							environment.getProperty(PayableConstant.UPDATE_SUCCESS_MESSAGE)));			
		}catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    IKResponse.<PayableDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<PayableDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(PayableConstant.UPDATE_ERROR_MESSAGE)));
        }
	}
}
