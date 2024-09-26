package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class UpdateBudget {
	private final BudgetRepository repository;
	
	public UpdateBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Budget execute(Budget budget, Long vehicleId) {
		return repository.updateBudget(budget, vehicleId);
	}
}
