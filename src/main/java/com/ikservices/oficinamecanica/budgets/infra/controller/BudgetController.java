package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.budgets.application.usecases.ChangeStatus;
import com.ikservices.oficinamecanica.budgets.application.usecases.GetBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.IncreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgets;
import com.ikservices.oficinamecanica.budgets.application.usecases.SaveBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.UpdateBudget;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKRes;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.vehicles.infra.constants.VehicleConstant;


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
	
	public BudgetController(GetBudget getBudget, ListBudgets listBudgets, SaveBudget saveBudget, 
			UpdateBudget updateBudget, ChangeStatus changeStatus, IncreaseAmount increaseAmount) {
		this.getBudget = getBudget;
		this.listBudgets = listBudgets;
		this.saveBudget = saveBudget;
		this.updateBudget = updateBudget;
		this.changeStatus = changeStatus;
		this.increaseAmount = increaseAmount;
	}
	
	@GetMapping("list/{vehicleId}")
	public ResponseEntity<IKRes<BudgetDTO>> listBudgets(@PathVariable Long vehicleId) {
		try {
			List<BudgetDTO> budgetDTOList = converter.parseBudgetDTOList(listBudgets.execute(vehicleId), vehicleId);

			return ResponseEntity.ok(IKRes.<BudgetDTO>build().body(budgetDTOList));

		} catch (Exception e) {
			String messageError = "VehicleId: " + vehicleId + " - " + e.getMessage();
			LOGGER.error(messageError, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKRes.<BudgetDTO>build().addMessage(environment.getProperty(BudgetConstant.LIST_ERROR_MESSAGE))
			);
		}
	}
	
	@GetMapping("get/{budgetId}")
	public ResponseEntity<IKRes<BudgetDTO>> getBudget(@PathVariable Long budgetId) {
		try {
			Map<Long, Map<Long, Budget>> outertMap = getBudget.execute(budgetId);

			Set<Map.Entry<Long, Map<Long, Budget>>> outerMapEntries = outertMap.entrySet();

			for (Map.Entry<Long, Map<Long, Budget>> innerMapEntries : outerMapEntries) {
				Long vehicleId = innerMapEntries.getKey();
				Budget budget = innerMapEntries.getValue().get(budgetId);
				return ResponseEntity.ok(IKRes.<BudgetDTO>build().body(new BudgetDTO(budget, budgetId, vehicleId)));
			}

			throw new IKException(
					Integer.parseInt(Objects.requireNonNull(environment.getProperty(BudgetConstant.GET_ERROR_CODE))),
					IKMessageType.getByCode(Integer.parseInt(Objects.requireNonNull(environment.getProperty(BudgetConstant.GET_ERROR_TYPE)))),
					environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)
			);

		}catch(IKException e) {
			return ResponseEntity.status(e.getCode()).body(IKRes.<BudgetDTO>build().addMessage(e.getMessage()));
		}catch (EntityNotFoundException e) {
			return ResponseEntity.status(Integer.parseInt(Objects.requireNonNull(environment.getProperty(BudgetConstant.GET_NOT_FOUND_CODE)))).body(
					IKRes.<BudgetDTO>build().addMessage(environment.getProperty(BudgetConstant.GET_NOT_FOUND_MESSAGE)));
		} catch (Exception e) {
			return ResponseEntity.status(Integer.parseInt(Objects.requireNonNull(environment.getProperty(BudgetConstant.GET_ERROR_CODE)))).body(
					IKRes.<BudgetDTO>build().addMessage(environment.getProperty(BudgetConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKRes<BudgetDTO>> saveBudget(@RequestBody BudgetDTO budgetDTO, UriComponentsBuilder uriBuilder) {
		String budgetJSON = IKLoggerUtil.parseJSON(budgetDTO);
		String loggerID = IKLoggerUtil.getLoggerID();
		try {
			Map<Long, Budget> budgetMap = saveBudget.execute(converter.parseBudget(budgetDTO), budgetDTO.vehicle.getVehicleId());
			
			ResponseEntity<IKRes<BudgetDTO>> responseEntity = null;
			
			for(Long budgetId : budgetMap.keySet()) {
				URI uri = uriBuilder.path("budgets/{budgetId}").buildAndExpand(budgetId).toUri();
				
				responseEntity = ResponseEntity.created(uri).body(IKRes.<BudgetDTO>build().body(
						converter.parseBudgetDTO(budgetMap, budgetDTO.getVehicle().getVehicleId()))
						.addMessage(environment.getProperty(BudgetConstant.SAVE_SUCCESS_MESSAGE)));
			}
			
			return responseEntity;
			
		}catch(IKException ike) {
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(code).body(IKRes.<BudgetDTO>build().addMessage(ike.getMessage()));
		}catch(Exception e) {
			LOGGER.error(loggerID + " - " + budgetJSON);
			LOGGER.error(loggerID + " - " + e.getMessage(), e);
			return ResponseEntity.status(Integer.parseInt(Objects.requireNonNull(environment.getProperty(BudgetConstant.SAVE_ERROR_CODE)))).body(
					IKRes.<BudgetDTO>build().addMessage(environment.getProperty(BudgetConstant.SAVE_ERROR_MESSAGE)));
		}
	}
}
