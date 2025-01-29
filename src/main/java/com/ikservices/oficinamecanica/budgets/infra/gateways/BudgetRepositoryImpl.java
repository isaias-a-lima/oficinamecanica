package com.ikservices.oficinamecanica.budgets.infra.gateways;

import java.math.BigDecimal;
import java.util.*;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgetsByEnum;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetRepositoryJPA;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceRepositoryJPA;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public class BudgetRepositoryImpl implements BudgetRepository {

	private static final int REMOVE = 0;
	private static final int UPDATE = 1;
	private static final int ADD = 2;
	
	private final BudgetConverter converter;
	private final BudgetItemServiceConverter budgetItemServiceConverter;
	private final BudgetRepositoryJPA repositoryJPA;
	private final BudgetItemServiceRepositoryJPA itemServiceRepository;
	
	public BudgetRepositoryImpl(BudgetConverter converter, BudgetItemServiceConverter budgetItemServiceConverter,
                                BudgetRepositoryJPA repositoryJPA,
								BudgetItemServiceRepositoryJPA itemServiceRepository) {
		this.converter = converter;
        this.budgetItemServiceConverter = budgetItemServiceConverter;
        this.repositoryJPA = repositoryJPA;
		this.itemServiceRepository = itemServiceRepository;
	}

	@Override
	public List<Map<Long, Map<Long, Budget>>> listBudgets(Long id, ListBudgetsByEnum listBy, BudgetStatusEnum status) {

		if (ListBudgetsByEnum.WORKSHOP.equals(listBy)) {
			return converter.parseBudgetList(repositoryJPA.getBudgetsByWorkshop(id, status));
		}
		return converter.parseBudgetList(repositoryJPA.getBudgetsByVehicle(id, status));
	}

	@Override
	public List<Map<Long, Map<Long, Budget>>> listBudgets(Long workshopId, String idDoc, BudgetStatusEnum status) {
		List<BudgetEntity> entities = repositoryJPA.getBudgetsByCustomer(workshopId, idDoc, status);
		return converter.parseBudgetList(entities);
	}

	@Override
	public Map<Long, Map<Long, Budget>> getBudget(Long budgetId) {
		BudgetEntity budgetEntity = repositoryJPA.getById(budgetId);

		Budget budget = converter.parseBudget(budgetEntity, false);

		Map<Long, Budget> innerMap = new HashMap<>();
		innerMap.put(budgetEntity.getBudgetId(), budget);

		Map<Long, Map<Long, Budget>> outerMap = new HashMap<>();
		outerMap.put(budgetEntity.getVehicleId(), innerMap);
		
		return outerMap;
	}

	@Override
	public Map<Long, Budget> saveBudget(Budget budget, Long vehicleId) {
		Map<Long, Budget> budgetMap = new HashMap<>();

		BudgetEntity budgetEntity = converter.parseEntity(budget, vehicleId, null);

		budgetEntity.setServiceItems(new ArrayList<>());

		BudgetEntity savedBudget = repositoryJPA.save(budgetEntity);

		if (Objects.nonNull(budget.getServiceItems()) && !budget.getServiceItems().isEmpty()) {

			Optional<BudgetEntity> optional = repositoryJPA.findById(savedBudget.getBudgetId());

			if (optional.isPresent()) {

				BudgetEntity entity = optional.get();

				for (BudgetItemService serviceItem : budget.getServiceItems()) {
					serviceItem.setItemId(new BudgetItemServiceId(null, entity.getBudgetId()));
					entity.addServiceItem(budgetItemServiceConverter.parseEntity(serviceItem));
				}

				budgetMap.put(entity.getBudgetId(), converter.parseBudget(entity, false));

				return budgetMap;

			} else {
				throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), "Erro ao tentar salvar."));
			}
		}

		budgetMap.put(savedBudget.getBudgetId(), converter.parseBudget(savedBudget, false));
		
		return budgetMap;
	}

	@Override
	public Map<Long, Budget> updateBudget(Budget budget, Long budgetId) {
		Map<Long, Budget> budgetMap = new HashMap<>();
		
		Optional<BudgetEntity> optional = repositoryJPA.findById(budgetId);

		if (optional.isPresent()) {
			BudgetEntity budgetEntity = optional.get();

			budgetEntity.update(converter.parseEntity(budget, budgetEntity.getVehicleId(), budgetId));

			budgetMap.put(budgetEntity.getBudgetId(), converter.parseBudget(budgetEntity, false));

			return budgetMap;
		}
		//TODO The message must come from a constant
		throw new IKException(new IKMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.WARNING.getCode(), "Orçamento não encontrado."));
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
