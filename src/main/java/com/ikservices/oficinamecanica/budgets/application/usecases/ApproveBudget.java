package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;

public class ApproveBudget {
    private final BudgetRepository repository;

    public ApproveBudget(BudgetRepository repository) {
        this.repository = repository;
    }

    public Long execute(Long budgetId) {
        return repository.approveBudget(budgetId);
    }
}
