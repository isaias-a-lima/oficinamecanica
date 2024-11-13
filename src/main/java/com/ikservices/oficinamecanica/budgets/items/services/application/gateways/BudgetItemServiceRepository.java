package com.ikservices.oficinamecanica.budgets.items.services.application.gateways;

import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;

public interface BudgetItemServiceRepository {
	List<Map<Long, BudgetItemService>> listBudgetItemServices(Long budgetId);
	Map<Long, BudgetItemService> getBudgetItemService(Long itemId);
	Map<Long, BudgetItemService> saveBudgetItemService(BudgetItemService item, Long budgetId);
	Map<Long, BudgetItemService> updateBudgetItemService(BudgetItemService item,  Long itemId);
	public void deleteBudgetItemService(Long itemId);
}
