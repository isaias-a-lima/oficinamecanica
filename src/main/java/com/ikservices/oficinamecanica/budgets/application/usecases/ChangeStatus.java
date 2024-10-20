package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;

public class ChangeStatus {
	private final BudgetRepository repository;
	
	public ChangeStatus(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public void execute(Long budgetId, BudgetStatusEnum budgetStatus) {
		repository.changeStatus(budgetId, budgetStatus);
	}
}
