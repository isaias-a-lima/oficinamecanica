package com.ikservices.oficinamecanica.budgets.infra.gateways;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetRepositoryJPA;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public class BudgetRepositoryImpl implements BudgetRepository {
	
	private final BudgetConverter converter;
	private final BudgetRepositoryJPA repositoryJPA;
	
	public BudgetRepositoryImpl(BudgetConverter converter,
			BudgetRepositoryJPA repositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}

	@Override
	public List<Map<Long, Budget>> listBudgets(Long vehicleId) {
		VehicleEntity vehicleEntity = new VehicleEntity();
		vehicleEntity.setVehicleId(vehicleId);
		return converter.parseBudgetList(repositoryJPA.findAllByVehicleEntity(vehicleEntity));
	}

	@Override
	public Map<Long, Map<Long, Budget>> getBudget(Long budgetId) {
		BudgetEntity budgetEntity = repositoryJPA.getById(budgetId);

		Budget budget = converter.parseBudget(budgetEntity);

		Map<Long, Budget> innerMap = new HashMap<>();
		innerMap.put(budgetEntity.getBudgetId(), budget);

		Map<Long, Map<Long, Budget>> outerMap = new HashMap<>();
		outerMap.put(budgetEntity.getVehicleId(), innerMap);
		
		return outerMap;
	}

	@Override
	public Map<Long, Budget> saveBudget(Budget budget, Long vehicleId) {
		BudgetEntity budgetEntity = new BudgetEntity();
		VehicleEntity vehicleEntity = new VehicleEntity();
		vehicleEntity.setVehicleId(vehicleId);
		
		budgetEntity.setAmount(budget.getAmount());
		budgetEntity.setBudgetStatus(budget.getBudgetStatus());
		budgetEntity.setKm(budget.getKm());
		budgetEntity.setOpeningDate(budget.getOpeningDate());
		budgetEntity.setVehicleId(vehicleId);

		BudgetEntity savedBudget = repositoryJPA.save(budgetEntity);

		Map<Long, Budget> budgetMap = new HashMap<>();
		budgetMap.put(savedBudget.getBudgetId(), converter.parseBudget(savedBudget));
		
		return budgetMap;
	}

	@Override
	public Map<Long, Budget> updateBudget(Budget budget, Long budgetId) {
		
		Optional<BudgetEntity> optional = repositoryJPA.findById(budgetId);
		BudgetEntity budgetEntity = optional.orElse(null);
		
		budgetEntity.update(converter.parseEntity(budget));
		
		Map<Long, Budget> budgetMap = new HashMap<>();
		budgetMap.put(budgetEntity.getBudgetId(), converter.parseBudget(budgetEntity));
		
		return budgetMap;
	}

	@Override
	public void changeStatus(Long budgetId, BudgetStatusEnum budgetStatus) {
		Optional<BudgetEntity> optional = repositoryJPA.findById(budgetId);
		BudgetEntity budgetEntity = optional.orElse(null);
		
		if(Objects.nonNull(budgetEntity)) {
			budgetEntity.setBudgetStatus(budgetStatus);	
		}
	}

	@Override
	public void increaseAmount(Long budgetId, BigDecimal value) {
		Optional<BudgetEntity> optional = repositoryJPA.findById(budgetId);
		BudgetEntity budgetEntity = optional.orElse(null);
		
		if(Objects.nonNull(budgetEntity)) {
			budgetEntity.setAmount(budgetEntity.getAmount().add(value));
		}
	}

	@Override
	public void decreaseAmount(Long budgetId, BigDecimal value) {
		Optional<BudgetEntity> optional = repositoryJPA.findById(budgetId);
		BudgetEntity budgetEntity = optional.orElse(null);
		
		if(Objects.nonNull(budgetEntity)) {
			budgetEntity.setAmount(budgetEntity.getAmount().subtract(value));
		}
	}	
}
