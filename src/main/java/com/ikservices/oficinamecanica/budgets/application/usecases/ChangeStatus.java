package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;

public class ChangeStatus {
	private final BudgetRepository repository;
	
	public ChangeStatus(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public void execute(Long budgetId, Character budgetStatus) {
		repository.changeStatus(budgetId, budgetStatus);
	}
}
