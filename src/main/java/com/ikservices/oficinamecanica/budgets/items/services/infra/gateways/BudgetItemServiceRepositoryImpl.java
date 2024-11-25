package com.ikservices.oficinamecanica.budgets.items.services.infra.gateways;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemServiceId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntityId;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceRepositoryJPA;

public class BudgetItemServiceRepositoryImpl implements BudgetItemServiceRepository {
	
	private BudgetItemServiceConverter converter;
	private BudgetItemServiceRepositoryJPA repositoryJPA;
	
	public BudgetItemServiceRepositoryImpl(BudgetItemServiceConverter converter, 
			BudgetItemServiceRepositoryJPA repositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}
	
	@Override
	public List<BudgetItemService> listBudgetItemServices(Long budgetId) {
		BudgetEntity budgetEntity = new BudgetEntity();
		budgetEntity.setBudgetId(budgetId);
		return converter.parseDomainList(repositoryJPA.findAllByBudgetEntity(budgetEntity));
	}

	@Override
	public BudgetItemService getBudgetItemService(BudgetItemServiceId itemId) {
		if(Objects.nonNull(itemId)) {
			BudgetItemServiceEntity itemEntity = repositoryJPA.getById(new BudgetItemServiceEntityId(
					itemId.getId(), itemId.getBudgetId()));			
			
			BudgetItemService item = converter.parseDomain(itemEntity);
			
			return item;
		}
		return null;
	}

	@Override
	public BudgetItemService saveBudgetItemService(BudgetItemService item) {
		BudgetItemServiceEntity savedEntity = repositoryJPA.save(converter.parseEntity(item));

		return converter.parseDomain(savedEntity);
	}

	@Override
	public BudgetItemService updateBudgetItemService(BudgetItemService item) {
		 Optional<BudgetItemServiceEntity> optional = repositoryJPA.findById(
				new BudgetItemServiceEntityId(item.getItemId().getId(), item.getItemId().getBudgetId()));
		
		 BudgetItemServiceEntity entity = optional.orElse(null);
		 
		 entity.update(converter.parseEntity(item));
		 
		return converter.parseDomain(entity);
	}

	@Override
	public void deleteBudgetItemService(BudgetItemServiceId itemId) {
		repositoryJPA.deleteById(new BudgetItemServiceEntityId(itemId.getId(), itemId.getBudgetId()));
	}	
}
