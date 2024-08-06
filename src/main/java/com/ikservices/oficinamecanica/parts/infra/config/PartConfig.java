package com.ikservices.oficinamecanica.parts.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.application.usecases.GetNextPartId;
import com.ikservices.oficinamecanica.parts.application.usecases.GetPart;
import com.ikservices.oficinamecanica.parts.application.usecases.GetPartsList;
import com.ikservices.oficinamecanica.parts.application.usecases.SavePart;
import com.ikservices.oficinamecanica.parts.application.usecases.UpdatePart;
import com.ikservices.oficinamecanica.parts.infra.PartConverter;
import com.ikservices.oficinamecanica.parts.infra.gateway.PartRepositoryImpl;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartRepositoryJPA;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

@Configuration
@PropertySource(name="parts.properties", value="classpath:parts.properties", encoding="UTF-8")
public class PartConfig {
	@Autowired
	Environment enviroment;
	
	@Bean
	public PartRepository partRepository(PartConverter converter, PartRepositoryJPA repository) {
		return new PartRepositoryImpl(converter, repository);
	}
	
	@Bean
	public PartConverter partConverter(WorkshopConverter workshopConverter) {
		return new PartConverter(workshopConverter);
	}
	
	@Bean
	public GetPart getPart(PartRepository repository) {
		return new GetPart(repository);
	}
	
	@Bean
	public GetPartsList getPartsList(PartRepository repository) {
		return new GetPartsList(repository);
	}
	
	@Bean
	public SavePart savePart(PartRepository repository) {
		return new SavePart(repository);
	}
	
	@Bean
	public UpdatePart updatePart(PartRepository repository) {
		return new UpdatePart(repository);
	}
	
	@Bean
	public GetNextPartId getNextPartId(PartRepository repository) {
		return new GetNextPartId(repository);
	}
}
