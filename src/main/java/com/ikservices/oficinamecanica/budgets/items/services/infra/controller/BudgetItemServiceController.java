package com.ikservices.oficinamecanica.budgets.items.services.infra.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.DeleteBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.GetBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.ListBudgetItemServices;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.SaveBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.UpdateBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.constants.BudgetItemServiceConstant;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;

@RestController
@RequestMapping("/items")
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
	
	public BudgetItemServiceController(BudgetItemServiceConverter converter, 
			GetBudgetItemService getBudgetItemService, ListBudgetItemServices listBudgetItemServices,
			SaveBudgetItemService saveBudgetItemService, UpdateBudgetItemService updateBudgetItemService,
			DeleteBudgetItemService deleteBudgetItemService) {
		this.converter = converter;
		this.getBudgetItemService = getBudgetItemService;
		this.listBudgetItemServices = listBudgetItemServices;
		this.saveBudgetItemService = saveBudgetItemService;
		this.updateBudgetItemService = updateBudgetItemService;
		this.deleteBudgetItemService = deleteBudgetItemService;
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
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetItemServiceResponseDTO>build().addMessage(
							Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetItemServiceConstant.GET_ERROR_MESSAGE)));
		}
	}
}
