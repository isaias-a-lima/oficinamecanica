package com.ikservices.oficinamecanica.payables.infra.config;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.application.usecases.*;
import com.ikservices.oficinamecanica.payables.infra.PayableConverter;
import com.ikservices.oficinamecanica.payables.infra.gateways.PayableRepositoryImpl;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(name="payable.properties", value="classpath:payable.properties", encoding = "UTF-8")
public class PayableConfig {
	@Autowired
	Environment environment;
	
	@Bean
	public PayableRepository payableRepository(PayableConverter converter, PayableRepositoryJPA repositoryJPA) {
		return new PayableRepositoryImpl(converter, repositoryJPA);
	}
	
	@Bean
	public GetPayable getPayable(PayableRepository repository) {
		return new GetPayable(repository);
	}
	
	@Bean
	public ListPayable listPayable(PayableRepository repository) {
		return new ListPayable(repository);
	}
	
	@Bean
	public SavePayable savePayable(PayableRepository repository) {
		return new SavePayable(repository);
	}
	
	@Bean
	public UpdatePayable updatePayable(PayableRepository repository) {
		return new UpdatePayable(repository);
	}
	
	@Bean
	public GetNextPayableId getNextPayableId(PayableRepository repository) {
		return new GetNextPayableId(repository);
	}

	@Bean
	public ListPayableBySupplier listPayableBySupplier(PayableRepository repository) {
		return new ListPayableBySupplier(repository);
	}
}
