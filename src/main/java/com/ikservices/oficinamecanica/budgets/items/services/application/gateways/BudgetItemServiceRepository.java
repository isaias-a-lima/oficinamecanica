package com.ikservices.oficinamecanica.budgets.items.services.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;

public interface BudgetItemServiceRepository {
	List<BudgetItemService> listBudgetItemServices(Long budgetId);
	BudgetItemService getBudgetItemService(BudgetItemServiceEntityId itemId);
	BudgetItemService saveBudgetItemService(BudgetItemService item);
	BudgetItemService updateBudgetItemService(BudgetItemService item);
	public void deleteBudgetItemService(BudgetItemServiceEntityId itemId);
}