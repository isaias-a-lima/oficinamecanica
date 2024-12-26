package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;

public class GetNextItemId {
	private final BudgetItemServiceRepository repository;
	
	public GetNextItemId(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public Long execute(Long budgetId) {
		return this.repository.getNextItemId(budgetId);
	}
}
