package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

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
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKRes;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.vehicles.infra.constants.VehicleConstant;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleResponse;


@RestController
@RequestMapping("budgets")
public class BudgetController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(BudgetController.class);
	
	@Autowired
	private Environment environment;
	@Autowired
	@Lazy
	private BudgetConverter converter;
	
	private GetBudget getBudget;
	private ListBudgets listBudgets;
	private SaveBudget saveBudget;
	private UpdateBudget updateBudget;
	private ChangeStatus changeStatus;
	private IncreaseAmount increaseAmount;
	
	public BudgetController(GetBudget getBudget, ListBudgets listBudgets, SaveBudget saveBudget, 
			UpdateBudget updateBudget, ChangeStatus changeStatus, IncreaseAmount increaseAmount) {
		this.getBudget = getBudget;
		this.listBudgets = listBudgets;
		this.saveBudget = saveBudget;
		this.updateBudget = updateBudget;
		this.changeStatus = changeStatus;
		this.increaseAmount = increaseAmount;
	}
	
	@GetMapping("{vehicleId}")
	public ResponseEntity<IKRes<BudgetDTO>> listBudgets(@PathVariable Long vehicleId) {
		List<BudgetDTO> budgetList = converter.parseToDTO(listBudgets.execute(vehicleId), vehicleId);
		
		return ResponseEntity.ok(IKRes.<BudgetDTO>build().body(budgetList));
	}
	
	@GetMapping("{budgetId}/{vehicleId}")
	public ResponseEntity<IKRes<BudgetDTO>> getBudget(@PathVariable Long budgetId, 
			@PathVariable Long vehicleId) {
		try {
			Map<Long, Budget> entries = getBudget.execute(budgetId);
			BudgetDTO dto = converter.parseDTO(entries, vehicleId);
			
			return ResponseEntity.ok(IKRes.<BudgetDTO>build().body(dto));
		}catch(IKException e) {
			return ResponseEntity.status(e.getCode()).body(IKRes.<BudgetDTO>build().addMessage(e.getMessage()));
		}catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(IKRes.<BudgetDTO>build().addMessage("Orçamento não encontrado"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKRes.<BudgetDTO>build().addMessage(environment.getProperty(VehicleConstant.DEFAULT_SERVER_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKRes<BudgetDTO>> saveBudget(@RequestBody BudgetDTO budgetDTO, UriComponentsBuilder uriBuilder) {
		String budgetJSON = IKLoggerUtil.parseJSON(budgetDTO);
		try {
			Map<Long, Budget> budgetMap = saveBudget.execute(converter.parseBudget(budgetDTO), budgetDTO.getBudgetId());
			
			ResponseEntity<IKRes<BudgetDTO>> responseEntity = null;
			
			for(Long budgetId : budgetMap.keySet()) {
				URI uri = uriBuilder.path("budgets/{budgetId}").buildAndExpand(budgetId).toUri();
				
				responseEntity = ResponseEntity.created(uri).body(IKRes.<BudgetDTO>build().body(
						converter.parseDTO(budgetMap, budgetDTO.getVehicle().getVehicleId()))
						.addMessage("Orçamento salvo."));
			}
			
			return responseEntity;
			
		}catch(IKException ike) {
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			LOGGER.error(budgetJSON + " - " + ike.getMessage(), ike);
			return ResponseEntity.status(code).body(IKRes.<BudgetDTO>build().addMessage(ike.getMessage()));
		}catch(Exception e) {
			LOGGER.error(budgetJSON + " - " + e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKRes.<BudgetDTO>build().addMessage("Erro ao salvar"));
		}
	}
}
