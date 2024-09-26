package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class GetBudget {
	private final BudgetRepository repository;
	
	public GetBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Budget execute(Long budgetId) {
		return repository.getBudget(budgetId);
	}
}
