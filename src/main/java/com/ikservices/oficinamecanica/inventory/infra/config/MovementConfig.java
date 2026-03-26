package com.ikservices.oficinamecanica.inventory.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ikservices.oficinamecanica.inventory.MovementConverter;
import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetBalanceByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.ListMovementByPartAndType;
import com.ikservices.oficinamecanica.inventory.application.usecases.SaveMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.UpdateMovement;
import com.ikservices.oficinamecanica.inventory.infra.gateways.MovementRepositoryImpl;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementRepositoryJPA;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartRepositoryJPA;

@Configuration
@PropertySource(name="movement.properties", value="classpath:movement.properties", encoding="UTF-8")
public class MovementConfig {
	
	@Bean
	public MovementRepository MovementRepository(MovementConverter converter, 
			MovementRepositoryJPA repositoryJPA, PartRepositoryJPA partRepositoryJPA) {
		return new MovementRepositoryImpl(converter, repositoryJPA, partRepositoryJPA);
	}
	
	@Bean
	public SaveMovement saveMovement(MovementRepository repository) {
		return new SaveMovement(repository);
	}
	
	@Bean
	public GetMovement getMovement(MovementRepository repository) {
		return new GetMovement(repository);
	}
	
	@Bean
	public ListMovementByPartAndType listMovementByPartAndType(MovementRepository repository) {
		return new ListMovementByPartAndType(repository);
	}
	
	@Bean
	public GetBalanceByPart getBalanceByPart(MovementRepository repository) {
		return new GetBalanceByPart(repository);
	}
	
	@Bean
	public UpdateMovement updateMovement(MovementRepository repository) {
		return new UpdateMovement(repository);
	}
}
