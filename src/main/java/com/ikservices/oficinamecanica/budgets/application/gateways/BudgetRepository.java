package com.ikservices.oficinamecanica.budgets.application.gateways;

import java.math.BigDecimal;
import java.util.List;

import com.ikservices.oficinamecanica.budgets.domain.Budget;

public interface BudgetRepository {
	public List<Budget> listBudgets(Long vehicleId);
	public Budget getBudget(Long budgetId);
	public Budget saveBudget(Budget budget, Long vehicleId);
	public Budget updateBudget(Budget budget, Long vehicleId);
	public void changeStatus(Long budgetId, Character budgetStatus);
	public void increaseAmount(Long budgetId, BigDecimal value);
}
