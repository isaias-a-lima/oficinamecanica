package com.ikservices.oficinamecanica.budgets.application.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.budgets.domain.Budget;

public interface BudgetRepository {
	public List<Map<Long, Budget>> listBudgets(Long vehicleId);
	public Map<Long, Map<Long, Budget>> getBudget(Long budgetId);
	public Map<Long, Budget> saveBudget(Budget budget, Long vehicleId);
	public Budget updateBudget(Budget budget, Long vehicleId);
	public void changeStatus(Long budgetId, Character budgetStatus);
	public void increaseAmount(Long budgetId, BigDecimal value);
}
