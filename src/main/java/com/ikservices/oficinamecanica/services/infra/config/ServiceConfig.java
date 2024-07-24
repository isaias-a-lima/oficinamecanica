package com.ikservices.oficinamecanica.services.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.services.application.gateways.ServiceRepository;
import com.ikservices.oficinamecanica.services.application.usecases.GetNextServiceId;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.application.usecases.ListServices;
import com.ikservices.oficinamecanica.services.application.usecases.SaveService;
import com.ikservices.oficinamecanica.services.application.usecases.UpdateService;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.services.infra.gateway.ServiceRepositoryImpl;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceRepositoryJPA;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

@Configuration
@PropertySource(name="services.properties", value="classpath:services.properties", encoding="UTF-8")
public class ServiceConfig {
	@Autowired
	Environment enviroment;
	
	@Bean
	public ServiceRepository serviceRepository(ServiceConverter converter, ServiceRepositoryJPA repository) {
		return new ServiceRepositoryImpl(converter, repository);
	}
	
	@Bean
	public GetService getService(ServiceRepository repository) {
		return new GetService(repository);
	}
	
	@Bean
	public ServiceConverter serviceConverter(WorkshopConverter workshopConverter) {
		return new ServiceConverter(workshopConverter);
	}
	
	@Bean
	public ListServices listServices(ServiceRepository repository) {
		return new ListServices(repository);
	}
	
	@Bean
	public SaveService saveService(ServiceRepository repository) {
		return new SaveService(repository);
	}
	
	@Bean
	public GetNextServiceId getNextServiceId(ServiceRepository repository) {
		return new GetNextServiceId(repository);
	}
	
	@Bean
	public UpdateService updateService(ServiceRepository repository) {
		return new UpdateService(repository);
	}
	
//	@Bean
//	public WorkshopConverter workshopConverter(UserConverter userConverter) {
//		return new WorkshopConverter(userConverter);
//	}
	
//	@Bean
//	public UserConverter userConverter() {
//		return new UserConverter();
//	}
}
