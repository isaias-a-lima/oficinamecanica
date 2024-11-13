package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class UpdateBudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public UpdateBudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, BudgetItemService> updateBudgetItemService(BudgetItemService item, Long itemId) {
		return repository.updateBudgetItemService(item, itemId);
	}
}
