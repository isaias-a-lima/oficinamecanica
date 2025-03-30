package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.application.usecases.business.ValidateApproved;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

import java.util.Map;

public class ApproveBudget {
    private final BudgetRepository repository;

    public ApproveBudget(BudgetRepository repository) {
        this.repository = repository;
    }

    public Long execute(Long budgetId) {

        Map<Long, Map<Long, Budget>> mapOutSide = repository.getBudget(budgetId);
        for (Long vehicleId : mapOutSide.keySet()) {
            Map<Long, Budget> budgetMap = mapOutSide.get(vehicleId);
            for (Long id : budgetMap.keySet()) {
                Budget budgetDB = budgetMap.get(id);
                ValidateApproved.execute(budgetDB);
            }
        }

        return repository.approveBudget(budgetId);
    }
}
