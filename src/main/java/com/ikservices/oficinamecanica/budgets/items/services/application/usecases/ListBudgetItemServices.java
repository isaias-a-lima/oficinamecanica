package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class ListBudgetItemServices {
	private final BudgetItemServiceRepository repository;
	
	public ListBudgetItemServices(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public List<Map<Long, BudgetItemService>> execute(Long budgetId) {
		return repository.listBudgetItemServices(budgetId);
	}
}
