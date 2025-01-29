package com.ikservices.oficinamecanica.budgets.application.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgetsByEnum;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;

public interface BudgetRepository {
	public List<Map<Long, Map<Long, Budget>>> listBudgets(Long id, ListBudgetsByEnum listBy, BudgetStatusEnum statusEnum);
	public List<Map<Long, Map<Long, Budget>>> listBudgets(Long workshopId, String idDoc, BudgetStatusEnum status);
	public Map<Long, Map<Long, Budget>> getBudget(Long budgetId);
	public Map<Long, Budget> saveBudget(Budget budget, Long vehicleId);
	public Map<Long, Budget> updateBudget(Budget budget, Long budgetId);
	public void changeStatus(Long budgetId, BudgetStatusEnum budgetStatus);
	public void increaseAmount(Long budgetId, BigDecimal value);
	public void decreaseAmount(Long budgetId, BigDecimal value);
}
