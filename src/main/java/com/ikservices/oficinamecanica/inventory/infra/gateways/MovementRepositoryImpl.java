package com.ikservices.oficinamecanica.inventory.infra.gateways;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.inventory.MovementConverter;
import com.ikservices.oficinamecanica.inventory.application.gateways.MovementRepository;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementEntityId;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementRepositoryJPA;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartRepositoryJPA;

public class MovementRepositoryImpl implements MovementRepository {
	private final MovementConverter converter;
	private final MovementRepositoryJPA repositoryJPA;
	private final PartRepositoryJPA partRepositoryJPA;
	
	public MovementRepositoryImpl(MovementConverter converter, MovementRepositoryJPA repositoryJPA,
			PartRepositoryJPA partRepositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
		this.partRepositoryJPA = partRepositoryJPA;
	}
	
	@Override
	public InventoryMovement saveMovement(InventoryMovement movement) {
		return converter.parseEntityToDomain(repositoryJPA.save(converter.parseDomainToEntity(movement)));
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
	public Integer getBalanceByPart(Integer partId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryMovement updateMovement(InventoryMovement movement) {
		// TODO Auto-generated method stub
		return null;
	}

}
