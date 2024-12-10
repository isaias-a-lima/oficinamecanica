package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;

public class DeleteBudgetItemService {
	private final BudgetItemServiceRepository repository;
	private final BudgetRepository budgetRepository;
	
	public DeleteBudgetItemService(BudgetItemServiceRepository repository, BudgetRepository budgetRepository) {
		this.repository = repository;
		this.budgetRepository = budgetRepository;
	}
	
	public void execute(BudgetItemServiceId itemId) {
			
			BudgetItemService item = repository.getBudgetItemService(itemId);
			
			BigDecimal total = item.getTotal();
			
			repository.deleteBudgetItemService(itemId);
			
			budgetRepository.decreaseAmount(itemId.getBudgetId(), total);
		
	}
}
