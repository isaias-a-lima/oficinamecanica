package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;

public class DeleteBudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public DeleteBudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public void execute(BudgetItemServiceEntityId itemId) {
		this.repository.deleteBudgetItemService(itemId);
	}
}
