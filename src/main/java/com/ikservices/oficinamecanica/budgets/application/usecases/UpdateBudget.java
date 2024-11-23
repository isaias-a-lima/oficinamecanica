package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class UpdateBudget {
	private final BudgetRepository repository;
	
	public UpdateBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Budget> execute(Budget budget, Long budgetId) {
		return repository.updateBudget(budget, budgetId);
	}
}
