package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;

public class DecreaseAmount {
	private final BudgetRepository repository;
	
	public DecreaseAmount(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public void execute(Long budgetId, BigDecimal value) {
		repository.decreaseAmount(budgetId, value);
	}
}

