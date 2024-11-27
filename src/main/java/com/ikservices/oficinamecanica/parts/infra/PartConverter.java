package com.ikservices.oficinamecanica.parts.infra;

import java.util.*;

import com.ikservices.oficinamecanica.parts.application.PartException;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.parts.infra.controller.PartDTO;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntityId;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

public class PartConverter {
	
	private final WorkshopConverter workshopConverter;
	
	public PartConverter(WorkshopConverter workshopConverter) {
		this.workshopConverter = workshopConverter;
	}
	
	public Part parsePart(PartEntity entity) {
		if(Objects.isNull(entity)) {
			throw new PartException("Null Object");
		}
		
		Part part = new Part();
		part.setPartId(new PartId(entity.getId().getId(), entity.getId().getWorkshopId()));
		part.setWorkshop(Objects.nonNull(entity.getWorkshopEntity()) ? workshopConverter.parseWorkshop(entity.getWorkshopEntity()) : null);
		part.setDescription(entity.getDescription());
		part.setCost(entity.getCost());
		part.setBalance(entity.getBalance());
		part.setProfit(entity.getProfit());
		part.setBrand(entity.getBrand());
		
		return part;
	}
	
	public PartEntity parseEntity(Part part) {
		if(Objects.isNull(part)) {
			throw new PartException("Null Object");
		}
		
		PartEntity entity = new PartEntity();
		entity.setId(new PartEntityId(part.getPartId().getId(), 
				part.getPartId().getWorkshopId()));
		entity.setBalance(part.getBalance());
		entity.setCost(part.getCost());
		entity.setDescription(part.getDescription());
		entity.setProfit(part.getProfit());
		entity.setWorkshopEntity(Objects.nonNull(part.getWorkshop()) ? workshopConverter.parseWorkshopEntity(part.getWorkshop(), part.getPartId().getWorkshopId()): null);
		entity.setBrand(part.getBrand());
		
		return entity;
	}
	
	public List<Part> parsePartsList(List<PartEntity> partEntityList){
		List<Part> partsList = new ArrayList<>();
		
		if(Objects.nonNull(partEntityList) && !partEntityList.isEmpty()) {
			for (PartEntity entity : partEntityList) {
				partsList.add(this.parsePart(entity));
			}
		}
		
		return partsList;
	}
	
	public List<PartEntity> parsePartEntityList(List<Part> partsList) {
		List<PartEntity> partEntityList = new ArrayList<>();
		
		if(Objects.nonNull(partsList) && !partsList.isEmpty()) {
			for (Part part : partsList) {
				partEntityList.add(this.parseEntity(part));
			}
		}
		
		return partEntityList;
	}
	
	public PartDTO parseDTO(Part part) {
		if(Objects.isNull(part)) {
			throw new PartException("Null Object");
		}
		PartDTO dto = new PartDTO();
		
		dto.setBalance(part.getBalance());
		dto.setCost(part.getCost());
		dto.setDescription(part.getDescription());
		dto.setPartId(part.getPartId().getId());
		dto.setWorkshopId(part.getPartId().getWorkshopId());
		dto.setProfit(part.getProfit());
		dto.setBrand(part.getBrand());
		
		return dto;
	}
	
	public Part parsePart(PartDTO dto) {
		if(Objects.isNull(dto)) {
			throw new PartException("Null Object");
		}
		
		Part part = new Part();
		
		part.setBalance(dto.getBalance());
		part.setCost(dto.getCost());
		part.setDescription(dto.getDescription());
		part.setProfit(dto.getProfit());
		part.setPartId(new PartId(dto.getPartId(), dto.getWorkshopId()));
		part.setBrand(dto.getBrand());
		
		return part;
	}
	
	public List<PartDTO> parseDTOList(List<Part> partsList) {
		List<PartDTO> partDTOList = new ArrayList<>();
		
		if(Objects.nonNull(partsList) && !partsList.isEmpty()) {
			for (Part part : partsList) {
				partDTOList.add(this.parseDTO(part));
			}
		}
		
		return partDTOList;
	}

	public Set<PartDTO> parseDTOSet(Set<Part> partsList) {
		Set<PartDTO> partDTOset = new HashSet<>();
		if(Objects.nonNull(partsList) && !partsList.isEmpty()) {
			for (Part part : partsList) {
				partDTOset.add(this.parseDTO(part));
			}
		}
		return partDTOset;
	}

	public Set<Part> parsePartsSet(Set<PartEntity> partsEntity) {
		Set<Part> parts = new HashSet<>();
		if (Objects.nonNull(partsEntity) && !partsEntity.isEmpty()) {
			for (PartEntity partEntity : partsEntity) {
				parts.add(this.parsePart(partEntity));
			}
		}
		return parts;
	}
}
