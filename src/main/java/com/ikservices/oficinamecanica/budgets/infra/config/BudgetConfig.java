package com.ikservices.oficinamecanica.budgets.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.application.usecases.ChangeStatus;
import com.ikservices.oficinamecanica.budgets.application.usecases.DecreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.GetBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.IncreaseAmount;
import com.ikservices.oficinamecanica.budgets.application.usecases.ListBudgets;
import com.ikservices.oficinamecanica.budgets.application.usecases.SaveBudget;
import com.ikservices.oficinamecanica.budgets.application.usecases.UpdateBudget;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.gateways.BudgetRepositoryImpl;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetRepositoryJPA;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;

@Configuration
@PropertySource(name="budget.properties", value="classpath:budgets.properties", encoding="utf-8")
public class BudgetConfig {
	@Autowired
	Environment environment;
	
	@Bean
	BudgetRepository budgetRepository(BudgetConverter converter, BudgetRepositoryJPA repositoryJPA) {
		return new BudgetRepositoryImpl(converter, repositoryJPA);
	}
	
	@Bean
	GetBudget getBudget(BudgetRepository repository) {
		return new GetBudget(repository);
	}
	
	@Bean
	UpdateBudget updateBudget(BudgetRepository repository) {
		return new UpdateBudget(repository);
	}
	
	@Bean
	SaveBudget saveBudget(BudgetRepository repository) {
		return new SaveBudget(repository);
	}
	
	@Bean
	ListBudgets listBudgets(BudgetRepository repository) {
		return new ListBudgets(repository);
	}
	
	@Bean
	ChangeStatus changeStatus(BudgetRepository repository) {
		return new ChangeStatus(repository);
	}
	
	@Bean
	IncreaseAmount increaseAmount(BudgetRepository repository) {
		return new IncreaseAmount(repository);
	}
	
	@Bean
	DecreaseAmount decreaseAmount(BudgetRepository repository) {
		return new DecreaseAmount(repository);
	}
}
