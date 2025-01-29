package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;

public class SaveBudget {
	private final BudgetRepository repository;
	
	public SaveBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Budget> execute(Budget budget, Long vehicleId) {
		return repository.saveBudget(budget, vehicleId);
	}
}
