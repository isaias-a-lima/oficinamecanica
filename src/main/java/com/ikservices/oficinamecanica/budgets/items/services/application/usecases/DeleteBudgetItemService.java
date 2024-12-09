package com.ikservices.oficinamecanica.budgets.items.services.application.usecases;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceRepositoryJPA;
import com.ikservices.oficinamecanica.commons.exception.IKException;

public class DeleteBudgetItemService {
	private final BudgetItemServiceRepository repository;
	private final BudgetRepository budgetRepository;
	
	public DeleteBudgetItemService(BudgetItemServiceRepository repository,
			BudgetRepository budgetRepository, 
			BudgetItemServiceRepositoryJPA repositoryJPA) {
		this.repository = repository;
		this.budgetRepository = budgetRepository;
	}
	
	public void execute(BudgetItemServiceId itemId) {
		Map<Long, Map<Long, Budget>> budget = budgetRepository.getBudget(itemId.getBudgetId());
		
		for (Map.Entry<Long,Map<Long,Budget>> entry : budget.entrySet()) {
			/*for(Map<Long, Budget> budgetMap : entry.getValue().) {
				
			}
			
			budgetRepository.decreaseAmount(entry.getValue(), itemEntity.ge);*/
		}

		this.repository.deleteBudgetItemService(itemId);
	}
}
