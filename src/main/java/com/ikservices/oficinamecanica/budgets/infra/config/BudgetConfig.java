package com.ikservices.oficinamecanica.budgets.infra.config;

import com.ikservices.oficinamecanica.budgets.application.gateways.BudgetRepository;
import com.ikservices.oficinamecanica.budgets.application.usecases.*;
import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.budgets.infra.constants.BudgetConstant;
import com.ikservices.oficinamecanica.budgets.infra.gateways.BudgetRepositoryImpl;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetRepositoryJPA;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.BudgetItemPartConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.BudgetItemServiceConverter;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(name="budget.properties", value="classpath:budgets.properties", encoding="utf-8")
public class BudgetConfig {
	@Autowired
	Environment environment;
	
	@Bean
	BudgetRepository budgetRepository(BudgetConverter converter, BudgetItemServiceConverter budgetItemServiceConverter, BudgetItemPartConverter budgetItemPartConverter, BudgetRepositoryJPA repositoryJPA,
									  BudgetItemServiceRepositoryJPA itemServiceRepository) {
		return new BudgetRepositoryImpl(converter, budgetItemServiceConverter, budgetItemPartConverter, repositoryJPA, itemServiceRepository);
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

	@Bean
	CreateBudgetPDF getCreateBudgetPDF() {
		String pdfName = environment.getProperty(BudgetConstant.PDF_NAME);
		String logo = environment.getProperty(BudgetConstant.PDF_LOGO);
		String title = environment.getProperty(BudgetConstant.PDF_TITLE);
		return new CreateBudgetPDF(pdfName, logo, title);
	}
	@Bean
	ApproveBudget approveBudget(BudgetRepository repository) {
		return new ApproveBudget(repository);
	}
}
