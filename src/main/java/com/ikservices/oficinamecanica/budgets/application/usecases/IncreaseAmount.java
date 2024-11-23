package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;

public class IncreaseAmount {
	private final BudgetRepository repository;
	
	public IncreaseAmount(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public void execute(Long budgetId, BigDecimal value) {
		repository.increaseAmount(budgetId, value);
	}
}
