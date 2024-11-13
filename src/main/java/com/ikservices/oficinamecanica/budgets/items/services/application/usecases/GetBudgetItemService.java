package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class GetBudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public GetBudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, BudgetItemService> execute(Long itemId) {
		return repository.getBudgetItemService(itemId);
	}
}
