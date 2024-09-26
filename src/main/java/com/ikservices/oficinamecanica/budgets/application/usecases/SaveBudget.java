package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class SaveBudget {
	private final BudgetRepository repository;
	
	public SaveBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Budget execute(Budget budget, Long vehicleId) {
		return repository.saveBudget(budget, vehicleId);
	}
}
