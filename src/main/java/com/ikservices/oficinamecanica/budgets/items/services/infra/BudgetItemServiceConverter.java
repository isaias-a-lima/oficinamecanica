package com.ikservices.oficinamecanica.budgets.items.services.infra;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;

@Component
public class BudgetItemServiceConverter {
	private final BudgetConverter budgetConverter;
	private final ServiceConverter serviceConverter;
	
	public BudgetItemServiceConverter(BudgetConverter budgetConverter, ServiceConverter serviceConverter) {
		this.budgetConverter = budgetConverter;
		this.serviceConverter = serviceConverter;
	}
	
	public BudgetItemService parseItem(BudgetItemServiceEntity entity) {
		if(Objects.isNull(entity)) {
			throw new IKException("Null object.");
		}
		
		BudgetItemService item = new BudgetItemService();
		item.setDiscount(entity.getDiscount());
		item.setQuantity(entity.getQuantity());
		item.setService(serviceConverter.parseService(entity.getServiceEntity()));
		
		return item;
	}
	
	public BudgetItemServiceEntity parseEntity(Map<Long, Budget> budgetMap, BudgetItemService item) {
		if(Objects.isNull(item)) {
			throw new IKException("Null Object.");
		}
		
		BudgetItemServiceEntity entity = new BudgetItemServiceEntity();
		entity.setBudgetEntity(budgetConverter.parseEntity(item.getBudget()));
		return null;
	}
}
