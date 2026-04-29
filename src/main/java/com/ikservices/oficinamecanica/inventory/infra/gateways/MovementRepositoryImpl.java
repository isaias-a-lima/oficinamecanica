package com.ikservices.oficinamecanica.inventory.infra.gateways;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.inventory.MovementConverter;
import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;
import com.ikservices.oficinamecanica.inventory.infra.constants.MovementConstant;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementEntity;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementEntityId;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementRepositoryJPA;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartRepositoryJPA;

public class MovementRepositoryImpl implements MovementRepository {
	private final MovementConverter converter;
	private final MovementRepositoryJPA repositoryJPA;
	
	public MovementRepositoryImpl(MovementConverter converter, MovementRepositoryJPA repositoryJPA,
			PartRepositoryJPA partRepositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}
	
	@Override
	public InventoryMovement saveMovement(InventoryMovement movement) {
		InventoryMovement savedMovement = converter.parseEntityToDomain(repositoryJPA.save(converter.parseDomainToEntity(movement)));
		
		return savedMovement;
	}

	@Override
	public InventoryMovement getMovement(Long workshopId, Long inventoryId) {
		return converter.parseEntityToDomain(repositoryJPA.getById(new MovementEntityId(workshopId, inventoryId)));
	}

	@Override
	public List<InventoryMovement> ListMovementByPartAndType(Integer partId, Long workshopId, 
			LocalDate startDate, LocalDate endDate, MovementTypeEnum movementTypeEnum) {
		return converter.parseEntityToDomainList(repositoryJPA.listMovementByPartAndType(partId, workshopId, startDate, endDate, movementTypeEnum.toString()));
	}

	
	@Override
	public Integer getBalanceByPart(Integer partId, Long workshopId) {
		LocalDate lastFinalBalanceDate = repositoryJPA.getLastFinalBalanceDateByPart(partId, workshopId);
		Integer lastFinalBalanceValue = repositoryJPA.getLastFinalBalanceValueByPart(partId, workshopId, lastFinalBalanceDate);
		
		Integer movementQuantity = repositoryJPA.getMovementQuantityByPart(partId, workshopId, lastFinalBalanceDate);
		
		if(lastFinalBalanceDate == null || lastFinalBalanceValue == null) {
			throw new NullPointerException("Null object.");
		}
		
		return lastFinalBalanceValue + movementQuantity;
	}

	@Override
	public InventoryMovement updateMovement(InventoryMovement movement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDate getLastFinalBalanceDateByPart(Integer partId, Long workshopId) {
		return repositoryJPA.getLastFinalBalanceDateByPart(partId, workshopId);
	}

	@Override
	public Integer getLastFinalBalanceValueByPart(Integer partId, Long workshopId, LocalDate basedate) {
		return repositoryJPA.getLastFinalBalanceValueByPart(partId, workshopId, basedate);
	}

	@Override
	public Integer getMovementQuantityByPart(Integer partId, Long workshopId, LocalDate lastFinalBalanceDate) {
		return repositoryJPA.getMovementQuantityByPart(partId, workshopId, lastFinalBalanceDate);
	}

}
