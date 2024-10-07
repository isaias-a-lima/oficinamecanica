package com.ikservices.oficinamecanica.budgets.infra.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.budgets.application.usecases.ChangeStatus;
import com.ikservices.oficinamecanica.budgets.application.usecases.GetBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.IncreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgets;
import com.ikservices.oficinamecanica.budgets.application.usecases.SaveBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.UpdateBudget;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.commons.response.IKRes;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
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
		List<BudgetDTO> budgetList = converter.parseToDTO(listBudgets.execute(vehicleId));
		
		return ResponseEntity.ok(IKRes.<BudgetDTO>build().body(budgetList));
	}
}
