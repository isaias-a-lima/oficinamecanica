package com.ikservices.oficinamecanica.workorders.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.budgets.infra.BudgetConverter;
import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.application.usecases.GetWorkOrder;
import com.ikservices.oficinamecanica.workorders.application.usecases.ListWorkOrders;
import com.ikservices.oficinamecanica.workorders.application.usecases.SaveWorkOrder;
import com.ikservices.oficinamecanica.workorders.application.usecases.UpdateWorkOrder;
import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.gateways.WorkOrderRepositoryImpl;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderRepositoryJPA;
import com.ikservices.oficinamecanica.workorders.installments.infra.WorkOrderInstallmentConverter;

@Configuration
@PropertySource(name="workorders.properties", value="classpath:workorders.properties", encoding="UTF-8")
public class WorkOrderConfig {
	@Autowired
	Environment environment;
	
	@Bean
	public WorkOrderRepository workOrderRepository(WorkOrderConverter converter,
			WorkOrderRepositoryJPA repository) {
		return new WorkOrderRepositoryImpl(converter, repository);
	}
	
	@Bean
	public WorkOrderInstallmentConverter workOrderInstallmentConverter() {
		return new WorkOrderInstallmentConverter();
	}
	

	
	@Bean
	public GetWorkOrder getWorkOrder(WorkOrderRepository repository) {
		return new GetWorkOrder(repository);
	}
	
	@Bean
	public ListWorkOrders listWorkOrders(WorkOrderRepository repository) {
		return new ListWorkOrders(repository);
	}
	
	@Bean
	public SaveWorkOrder saveWorkOrder(WorkOrderRepository repository) {
		return new SaveWorkOrder(repository);
	}
	
	@Bean
	public UpdateWorkOrder updateWorkOrder(WorkOrderRepository repository) {
		return new UpdateWorkOrder(repository);
	}
}
