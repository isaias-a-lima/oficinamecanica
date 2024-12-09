package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public class SaveBudgetItemService {
	private final BudgetItemServiceRepository repository;
	private final BudgetRepository budgetRepository;
	
	public SaveBudgetItemService(BudgetItemServiceRepository repository, BudgetRepository budgetRepository) {
		this.repository = repository;
		this.budgetRepository = budgetRepository;
	}
	
	public BudgetItemService execute(BudgetItemService item){
		 BudgetItemService savedItem = repository.saveBudgetItemService(item);
		 budgetRepository.increaseAmount(savedItem.getItemId().getBudgetId(), 
				 savedItem.getTotal());
		 
		 return savedItem;
	}
}
