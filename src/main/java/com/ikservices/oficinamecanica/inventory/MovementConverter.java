package com.ikservices.oficinamecanica.inventory;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.inventory.domain.InventoryId;
import com.ikservices.oficinamecanica.inventory.domain.InventoryMovement;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.SourceEnum;
import com.ikservices.oficinamecanica.inventory.infra.controller.MovementDTO;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementEntity;
import com.ikservices.oficinamecanica.inventory.infra.persistence.MovementEntityId;

import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;

@Component
public class MovementConverter extends IKConverter<MovementDTO, InventoryMovement, MovementEntity, MovementDTO>{

	@Override
	public InventoryMovement parseRequestToDomain(MovementDTO request) {
		InventoryMovement domain = null;
		
		if(Objects.nonNull(request)) {
			domain = new InventoryMovement();
			domain.setId(new InventoryId(request.getWorkshopId(), request.getInventoryId()));
			domain.setMovementDate(request.getMovementDate());
			domain.setMovementType(MovementTypeEnum.findByIndex(request.getMovementType()));
			domain.setDocumentNumber(request.getDocumentNumber());
			domain.setSupplierId(request.getSupplierId());
			domain.setPartId(request.getPartId());
			domain.setQuantity(request.getQuantity());
			domain.setSourceEnum(SourceEnum.findByIndex(request.getSource()));
		}
		
		return domain;
	}

	@Override
	public MovementEntity parseDomainToEntity(InventoryMovement domain) {
		MovementEntity entity = null;
		
		if(Objects.nonNull(domain)) {
			entity = new MovementEntity();
			
			entity.setId(new MovementEntityId(domain.getId().getWorkshopId(), domain.getId().getInventoryId()));
			entity.setMovementDate(domain.getMovementDate());
			entity.setMovementType(domain.getMovementType());
			entity.setDocumentNumber(domain.getDocumentNumber());
			entity.setSupplierId(domain.getSupplierId());
			entity.setPartId(domain.getPartId());
			entity.setQuantity(domain.getQuantity());
			entity.setSource(domain.getSourceEnum());
		}
		
		return entity;
	}

	@Override
	public InventoryMovement parseEntityToDomain(MovementEntity entity) {
		InventoryMovement domain = null;
		
		if(Objects.nonNull(entity)) {
			domain = new InventoryMovement();
			
			domain.setId(new InventoryId(entity.getId().getWorkshopId(), entity.getId().getInventoryId()));
			domain.setMovementDate(entity.getMovementDate());
			domain.setMovementType(entity.getMovementType());
			domain.setDocumentNumber(entity.getDocumentNumber());
			domain.setSupplierId(entity.getSupplierId());
			domain.setPartId(entity.getPartId());
			domain.setQuantity(entity.getQuantity());
			domain.setSourceEnum(entity.getSource());
		}
		
		return domain;
	}

	@Override
	public MovementDTO parseDomainToResponse(InventoryMovement domain) {
		MovementDTO dto = null;
		
		if(Objects.nonNull(domain)) {
			dto = new MovementDTO();
			dto.setWorkshopId(domain.getId().getWorkshopId());
			dto.setInventoryId(domain.getId().getInventoryId());
			dto.setMovementDate(domain.getMovementDate());
			dto.setMovementType(domain.getMovementType().ordinal());
			dto.setDocumentNumber(domain.getDocumentNumber());
			dto.setSupplierId(domain.getSupplierId());
			dto.setPartId(domain.getPartId());
			dto.setQuantity(domain.getQuantity());
			dto.setSource(domain.getSourceEnum().ordinal());
		}
		
		return dto;
	}
}
