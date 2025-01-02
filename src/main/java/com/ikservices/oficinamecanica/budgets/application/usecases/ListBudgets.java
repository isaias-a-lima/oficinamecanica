package com.ikservices.oficinamecanica.budgets.application.usecases;

import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;

public class ListBudgets {
	private final BudgetRepository repository;
	
	public ListBudgets(BudgetRepository repository) {
		this.repository = repository;
	}
	
	public List<Map<Long, Map<Long, Budget>>> execute(Long vehicleId) {
		return repository.listBudgets(vehicleId);
	}

	public List<Map<Long, Map<Long, Budget>>> execute(Long workshopId, String idDoc, BudgetStatusEnum status) {
		return repository.listBudgets(workshopId, idDoc, status);
	}
}
