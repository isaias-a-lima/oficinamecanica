package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class SavebudgetItemService {
	private final BudgetItemServiceRepository repository;
	
	public SavebudgetItemService(BudgetItemServiceRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, BudgetItemService> execute(BudgetItemService item, Long budgetId){
		return repository.saveBudgetItemService(item, budgetId);
	}
}
