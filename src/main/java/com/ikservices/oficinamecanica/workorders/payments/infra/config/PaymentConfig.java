package com.ikservices.oficinamecanica.workorders.payments.infra.config;

import com.ikservices.oficinamecanica.workorders.infra.WorkOrderConverter;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderRepositoryJPA;
import com.ikservices.oficinamecanica.workorders.payments.application.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.workorders.payments.application.gateways.PaymentRepository;
import com.ikservices.oficinamecanica.workorders.payments.infra.PaymentConverter;
import com.ikservices.oficinamecanica.workorders.payments.infra.gateways.PaymentRepositoryImpl;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentRepositoryJPA;

@Configuration
@PropertySource(name="payments.properties", value ="classpath:payments.properties", encoding="UTF-8")
public class PaymentConfig {
	@Autowired
	Environment environment;
	
	@Bean
	public PaymentRepository paymentRepository(PaymentConverter converter,
											   PaymentRepositoryJPA repositoryJPA,
											   WorkOrderRepositoryJPA workOrderRepositoryJPA,
											   WorkOrderConverter workOrderConverter) {
		return new PaymentRepositoryImpl(converter, repositoryJPA, workOrderRepositoryJPA, workOrderConverter);
	}
	
	@Bean
	public ListPayments listPayments(PaymentRepository repository) {
		return new ListPayments(repository);
	}
	
	@Bean
	public GetPayment getPayment(PaymentRepository repository) {
		return new GetPayment(repository);
	}
	
	@Bean
	public ListOverduePayments listOverduePayments(PaymentRepository repository) {
		return new ListOverduePayments(repository);
	}
	
	@Bean
	public ListPaymentsByDuePeriod listPaymentsByDuePeriod(PaymentRepository repository) {
		return new ListPaymentsByDuePeriod(repository);
	}

	@Bean
	public UpdatePayment updatePayment(PaymentRepository repository) {
		return new UpdatePayment(repository);
	}
}
