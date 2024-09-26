package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;

public class ListBudgets {
	private final BudgetRepository repository;
	
	public ListBudgets(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public List<Budget> execute(Long vehicleId) {
		return repository.listBudgets(vehicleId);
	}
}
