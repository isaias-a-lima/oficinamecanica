package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.application.usecases.business.ValidateApproved;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class UpdateBudget {
	private final BudgetRepository repository;
	
	public UpdateBudget(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Budget> execute(Budget budget, Long budgetId) {

		Map<Long, Map<Long, Budget>> mapOutSide = repository.getBudget(budgetId);
		for (Long vehicleId : mapOutSide.keySet()) {
			Map<Long, Budget> budgetMap = mapOutSide.get(vehicleId);
			for (Long id : budgetMap.keySet()) {
				Budget budgetDB = budgetMap.get(id);
				ValidateApproved.execute(budgetDB);
			}
		}

		return repository.updateBudget(budget, budgetId);
	}
}
