package com.ikservices.oficinamecanica.inventory.infra.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetBalanceByPart;
import com.ikservices.oficinamecanica.inventory.application.usecases.GetMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.ListMovementByPartAndType;
import com.ikservices.oficinamecanica.inventory.application.usecases.SaveMovement;
import com.ikservices.oficinamecanica.inventory.application.usecases.UpdateMovement;

@Configuration
@PropertySource(name="movement.properties", value="classpath:movement.properties", encoding="UTF-8")
public class MovementConfig {
	
	public SaveMovement saveMovement(MovementRepository repository) {
		return new SaveMovement(repository);
	}
	
	public GetMovement getMovement(MovementRepository repository) {
		return new GetMovement(repository);
	}
	
	public ListMovementByPartAndType listMovementByPartAndType(MovementRepository repository) {
		return new ListMovementByPartAndType(repository);
	}
	
	public GetBalanceByPart getBalanceByPart(MovementRepository repository) {
		return new GetBalanceByPart(repository);
	}
	
	public UpdateMovement updateMovement(MovementRepository repository) {
		return new UpdateMovement(repository);
	}
}
