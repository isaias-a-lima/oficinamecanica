package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class UpdateBudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public UpdateBudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public BudgetItemService execute(BudgetItemService item) {
		return repository.updateBudgetItemService(item);
	}
}
