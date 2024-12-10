package com.ikservices.oficinamecanica.budgets.items.services.infra.controller;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.budgets.application.BudgetBusinessConstant;
import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.DeleteBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.GetBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.GetNextItemId;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.ListBudgetItemServices;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.SaveBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.UpdateBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.constants.BudgetItemServiceConstant;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;

@RestController
@RequestMapping("/budget/service-items")
public class BudgetItemServiceController {

	private static final Logger LOGGER = IKLoggerUtil.getLogger(BudgetItemServiceController.class);
	
	@Autowired
	private Environment environment;
	
	private final BudgetItemServiceConverter converter;
	private final GetBudgetItemService getBudgetItemService;
	private final ListBudgetItemServices listBudgetItemServices;
	private final SaveBudgetItemService saveBudgetItemService;
	private final UpdateBudgetItemService updateBudgetItemService;
	private final DeleteBudgetItemService deleteBudgetItemService;
	private final GetNextItemId getNextItemId;
	
	public BudgetItemServiceController(BudgetItemServiceConverter converter, 
			GetBudgetItemService getBudgetItemService, ListBudgetItemServices listBudgetItemServices,
			SaveBudgetItemService saveBudgetItemService, UpdateBudgetItemService updateBudgetItemService,
			DeleteBudgetItemService deleteBudgetItemService,
			GetNextItemId getNextItemId) {
		this.converter = converter;
		this.getBudgetItemService = getBudgetItemService;
		this.listBudgetItemServices = listBudgetItemServices;
		this.saveBudgetItemService = saveBudgetItemService;
		this.updateBudgetItemService = updateBudgetItemService;
		this.deleteBudgetItemService = deleteBudgetItemService;
		this.getNextItemId = getNextItemId;
	}
	
	@GetMapping("/{itemId}/{budgetId}")
	public ResponseEntity<IKResponse<BudgetItemServiceResponseDTO>> getBudgetItemService(@PathVariable Long itemId, @PathVariable Long budgetId) {
		try {
			BudgetItemService item = getBudgetItemService.execute(new BudgetItemServiceId(itemId, budgetId));
			return ResponseEntity.ok(IKResponse.<BudgetItemServiceResponseDTO>build().body(converter.parseItemToDTO(item)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<BudgetItemServiceResponseDTO>build()
					.addMessage(ike.getIkMessage().getCode(), 
							IKMessageType.getByCode(ike.getIkMessage().getType()), 
							ike.getIkMessage().getMessage()));
		}catch (EntityNotFoundException e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING, environment.getProperty(BudgetItemServiceConstant.GET_NOT_FOUND_MESSAGE)));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetItemServiceConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("/{budgetId}")
	public ResponseEntity<IKResponse<BudgetItemServiceResponseDTO>> listBudgetItemServices(@PathVariable Long budgetId) {
		try {
			List<BudgetItemService> itemList = listBudgetItemServices.execute(budgetId);
			return ResponseEntity.ok(IKResponse.<BudgetItemServiceResponseDTO>build().body(converter.parseItemListToResponseList(itemList)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
					ike.getIkMessage().getCode(), 
					IKMessageType.getByCode(ike.getIkMessage().getType()), 
					ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetItemServiceConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<IKResponse<BudgetItemServiceResponseDTO>> saveBudgetItemServices(@RequestBody BudgetItemServiceRequestDTO item, UriComponentsBuilder uriBuilder) {
		try {
			Long nextItemId = getNextItemId.execute(item.getBudgetId());
			item.setItemId(nextItemId);
			BudgetItemService savedItem = saveBudgetItemService.execute(converter.parseDomain(item));
			
			URI uri = uriBuilder.path("budget/service-items/{itemId}/{budgetId}").buildAndExpand(savedItem.getItemId().getId(), 
					savedItem.getItemId().getBudgetId()).toUri();
			
			return ResponseEntity.created(uri).body(IKResponse.<BudgetItemServiceResponseDTO>build().body(converter.parseItemToDTO(savedItem)));
			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<BudgetItemServiceResponseDTO>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), 
							ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
							environment.getProperty(BudgetItemServiceConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<BudgetItemServiceResponseDTO>> updateBudgetItemService(@RequestBody BudgetItemServiceRequestDTO item) {
		try {
			BudgetItemService updateItem = updateBudgetItemService.execute(converter.parseDomain(item));	
			return ResponseEntity.ok(IKResponse.<BudgetItemServiceResponseDTO>build().body(converter.parseItemToDTO(updateItem)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							ike.getIkMessage().getCode(), 
							IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, 
							environment.getProperty(BudgetItemServiceConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
	
	@DeleteMapping("{id}/{budgetId}")
	@Transactional
	public ResponseEntity<IKResponse<String>> deleteBudgetItemService(@PathVariable Long id, @PathVariable Long budgetId) {
		try {
			deleteBudgetItemService.execute(new BudgetItemServiceId(id, budgetId));	
			return ResponseEntity.ok(IKResponse.<String>build().addMessage(
					Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, 
					environment.getProperty(BudgetItemServiceConstant.OPERATION_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<String>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<String>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.OPERATION_ERROR_MESSAGE)));
		}
	}
}
