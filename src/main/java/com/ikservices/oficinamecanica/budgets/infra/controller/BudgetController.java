package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.ikservices.oficinamecanica.budgets.application.BudgetBusinessConstant;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.budgets.application.usecases.ChangeStatus;
import com.ikservices.oficinamecanica.budgets.application.usecases.DecreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.GetBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.IncreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgets;
import com.ikservices.oficinamecanica.budgets.application.usecases.SaveBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.UpdateBudget;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;


@RestController
@RequestMapping("budgets")
public class BudgetController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(BudgetController.class);
	
	@Autowired
	private Environment environment;
	@Autowired
	@Lazy
	private BudgetConverter converter;
	
	private final GetBudget getBudget;
	private final ListBudgets listBudgets;
	private final SaveBudget saveBudget;
	private final UpdateBudget updateBudget;
	private final ChangeStatus changeStatus;
	private final IncreaseAmount increaseAmount;
	private final DecreaseAmount decreaseAmount;
	
	public BudgetController(GetBudget getBudget, ListBudgets listBudgets, SaveBudget saveBudget, 
			UpdateBudget updateBudget, ChangeStatus changeStatus, IncreaseAmount increaseAmount,
			DecreaseAmount decreaseAmount) {
		this.getBudget = getBudget;
		this.listBudgets = listBudgets;
		this.saveBudget = saveBudget;
		this.updateBudget = updateBudget;
		this.changeStatus = changeStatus;
		this.increaseAmount = increaseAmount;
		this.decreaseAmount = decreaseAmount;
	}
	
	@GetMapping("list/{vehicleId}")
	public ResponseEntity<IKResponse<BudgetDTO>> listBudgets(@PathVariable Long vehicleId) {
		try {
			List<BudgetDTO> budgetDTOList = converter.parseBudgetDTOList(listBudgets.execute(vehicleId));

			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(budgetDTOList));

		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.LIST_ERROR_MESSAGE))
			);
		}
	}

	@GetMapping("list/{workshopId}/{idDoc}/{status}")
	public ResponseEntity<IKResponse<BudgetDTO>> listBudgetsByCustomer(@PathVariable Long workshopId, @PathVariable String idDoc, @PathVariable int status) {
		try {
			List<BudgetDTO> budgetDTOList = converter.parseBudgetDTOList(listBudgets.execute(workshopId, idDoc, BudgetStatusEnum.findByIndex(status)));

			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(budgetDTOList));

		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.LIST_ERROR_MESSAGE))
			);
		}
	}
	
	@GetMapping("get/{budgetId}")
	public ResponseEntity<IKResponse<BudgetDTO>> getBudget(@PathVariable Long budgetId) {
		try {
			Map<Long, Map<Long, Budget>> outertMap = getBudget.execute(budgetId);

			Set<Map.Entry<Long, Map<Long, Budget>>> outerMapEntries = outertMap.entrySet();

			for (Map.Entry<Long, Map<Long, Budget>> innerMapEntries : outerMapEntries) {
				Long vehicleId = innerMapEntries.getKey();
				Budget budget = innerMapEntries.getValue().get(budgetId);
				return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(new BudgetDTO(budget, budgetId, vehicleId)));
			}

			throw new IKException(new IKMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.WARNING.getCode(), environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)));

		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch (EntityNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.WARNING, environment.getProperty(BudgetConstant.GET_NOT_FOUND_MESSAGE)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKResponse<BudgetDTO>> saveBudget(@RequestBody BudgetDTO budgetDTO, UriComponentsBuilder uriBuilder) {
		String budgetJSON = IKLoggerUtil.parseJSON(budgetDTO);
		String loggerID = IKLoggerUtil.getLoggerID();
		try {
			Map<Long, Budget> budgetMap = saveBudget.execute(converter.parseBudget(budgetDTO), budgetDTO.vehicle.getVehicleId());
			
			ResponseEntity<IKResponse<BudgetDTO>> responseEntity = null;
			
			for(Long budgetId : budgetMap.keySet()) {
				URI uri = uriBuilder.path("budgets/{budgetId}").buildAndExpand(budgetId).toUri();
				
				responseEntity = ResponseEntity.created(uri).body(IKResponse.<BudgetDTO>build().body(
						converter.parseBudgetDTO(budgetMap, budgetDTO.getVehicle().getVehicleId()))
						.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.SAVE_SUCCESS_MESSAGE)));
			}
			
			return responseEntity;
			
		}catch(IKException ike) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	
	@Transactional
	@PutMapping
	public ResponseEntity<IKResponse<BudgetDTO>> updateBudget(@RequestBody BudgetDTO budgetDTO) {
		String budgetJSON = IKLoggerUtil.parseJSON(budgetDTO);
		String loggerID = IKLoggerUtil.getLoggerID();
		try {
			Map<Long, Budget> budgetMap = updateBudget.execute(converter.parseBudget(budgetDTO), budgetDTO.getBudgetId());
			
			return ResponseEntity.ok(IKResponse.<BudgetDTO>build().body(converter.parseBudgetDTO(budgetMap, null))
					.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.UPDATE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<BudgetDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<BudgetDTO>build().addMessage(BudgetBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(BudgetConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
	
	@Transactional
	@PutMapping("{budgetId}/{value}")
	public ResponseEntity<IKResponse<String>> increaseAmount(@PathVariable Long budgetId, @PathVariable BigDecimal value) {
		try {
			increaseAmount.execute(budgetId, value);
			
			return ResponseEntity.ok(IKResponse.<String>build()
					.addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
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
	
	@Transactional
	@PutMapping("changeStatus/{budgetId}/{budgetStatus}")
	public ResponseEntity<IKResponse<String>> changeStatus(@PathVariable Long budgetId, @PathVariable int budgetStatus) {
		try {
			changeStatus.execute(budgetId, BudgetStatusEnum.findByIndex(budgetStatus));
			return ResponseEntity.ok(IKResponse.<String>build().addMessage(BudgetBusinessConstant.SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
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
	
	@Transactional
	@PutMapping("decreaseAmount/{budgetId}/{value}")
	public ResponseEntity<IKResponse<String>> decreaseAmount(@PathVariable Long budgetId, 
			@PathVariable BigDecimal value) {
		try {
			decreaseAmount.execute(budgetId, value);
			return ResponseEntity.ok(IKResponse.<String>build().addMessage(BudgetBusinessConstant.SUCCESS_CODE,
					IKMessageType.SUCCESS, environment.getProperty(BudgetConstant.OPERATION_SUCCESS_MESSAGE)));
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
