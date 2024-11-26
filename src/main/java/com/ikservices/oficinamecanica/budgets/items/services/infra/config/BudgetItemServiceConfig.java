package com.ikservices.oficinamecanica.budgets.items.services.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.budgets.items.services.application.gateways.BudgetItemServiceRepository;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.DeleteBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.GetBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.GetNextItemId;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.ListBudgetItemServices;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.SaveBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.application.usecases.UpdateBudgetItemService;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.gateways.BudgetItemServiceRepositoryImpl;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceRepositoryJPA;

@Configuration
@PropertySource(name="budget_item_service.properties", value="classpath:budget_item_service.properties",
encoding ="utf-8")
public class BudgetItemServiceConfig {
	@Autowired
	Environment environment;
	
	@Bean
	BudgetItemServiceRepository budgetItemServiceRepository(BudgetItemServiceConverter converter, BudgetItemServiceRepositoryJPA repositoryJPA) {
		return new BudgetItemServiceRepositoryImpl(converter, repositoryJPA);
	}
	
	@Bean
	GetBudgetItemService getBudgetItemService(BudgetItemServiceRepository repository) {
		return new GetBudgetItemService(repository);
	}
	
	@Bean
	UpdateBudgetItemService updateBudgetItemService(BudgetItemServiceRepository repository) {
		return new UpdateBudgetItemService(repository);
	}
	
	@Bean
	SaveBudgetItemService saveBudgetItemService(BudgetItemServiceRepository repository) {
		return new SaveBudgetItemService(repository);
	}
	
	@Bean
	ListBudgetItemServices listBudgetItemService(BudgetItemServiceRepository repository) {
		return new ListBudgetItemServices(repository);
	}
	
	@Bean
	DeleteBudgetItemService deleteBudgetItemService(BudgetItemServiceRepository repository) {
		return new DeleteBudgetItemService(repository);
	}
	
	@Bean
	GetNextItemId getNextItemId(BudgetItemServiceRepository repository) {
		return new GetNextItemId(repository);
	}
}