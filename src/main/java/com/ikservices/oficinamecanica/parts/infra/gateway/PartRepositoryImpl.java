package com.ikservices.oficinamecanica.parts.infra.gateway;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.parts.infra.PartConverter;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntity;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartEntityId;
import com.ikservices.oficinamecanica.parts.infra.persistence.PartRepositoryJPA;

public class PartRepositoryImpl implements PartRepository{

	private final PartConverter converter;
	private final PartRepositoryJPA repository;
	
	public PartRepositoryImpl(PartConverter converter, 
			PartRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}
	
	@Override
	public Part savePart(Part part) {
		Optional<PartEntity> optional = repository.
				findById(new PartEntityId(part.getPartId().getId(), 
						part.getPartId().getWorkshopId()));
		if(optional.isPresent()) {
			throw new IKException(HttpStatus.FOUND.value(), 
					IKMessageType.WARNING, "Peça já cadastrada.");
		}
		PartEntity savedEntity = repository.save(converter.parseEntity(part));
		return converter.parsePart(savedEntity);
	}

	@Override
	public Part updatePart(Part part) {
		Optional<PartEntity> optional = repository.
				findById(new PartEntityId(part.getPartId().getId(), 
						part.getPartId().getWorkshopId()));
		PartEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseEntity(part));
		}
		
		return converter.parsePart(entity);
	}

	@Override
	public Part getPart(PartId id) {
		return this.converter.parsePart(repository.getById(new PartEntityId(id.getId(), id.getWorkshopId())));
	}

	@Override
	public List<Part> getPartsList(Long workshopId) {
		return this.converter.parsePartsList(repository.findAllByWorkshopId(workshopId));
	}

	@Override
	public Long getNextPartId(Long workshopId) {
		return repository.getNextPartId(workshopId);
	}

}
