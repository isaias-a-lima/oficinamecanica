package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;

public class GetBudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public GetBudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public BudgetItemService execute(BudgetItemServiceId itemId) {
		return repository.getBudgetItemService(itemId);
	}
}
