package com.ikservices.oficinamecanica.payables.infra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.categories.infra.CategoryConverter;
import com.ikservices.oficinamecanica.commons.generics.IKConverter;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;
import com.ikservices.oficinamecanica.payables.infra.controller.PayableDTO;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableEntity;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableEntityId;

@Component
public class PayableConverter extends IKConverter<PayableDTO, Payable, PayableEntity, PayableDTO>{
	
	@Autowired
	CategoryConverter categoryConverter;
	
	@Override
	public Payable parseRequestToDomain(PayableDTO dto) {
		Payable payable = null;
		
		if(Objects.nonNull(dto)) {
			payable = new Payable();
			
			payable.setId(new PayableId(dto.getId(), dto.getWorkshopId()));
			payable.setCreationDate(Objects.nonNull(dto.getCreationDate()) && 
					!dto.getCreationDate().isEmpty() ? 
					LocalDate.parse(dto.getCreationDate()) : null);
			payable.setDescription(dto.getDescription());
			payable.setDocNumber(dto.getDocNumber());
			payable.setDueDate(Objects.nonNull(dto.getDueDate()) 
					&& !dto.getDueDate().isEmpty() ? 
					LocalDate.parse(dto.getDueDate()) : null);
			payable.setPayValue(NumberUtil.parseBigDecimal(dto.getPayValue()));
			payable.setPayDate(Objects.nonNull(dto.getPayDate()) 
					&& !dto.getPayDate().isEmpty() ? 
					LocalDate.parse(dto.getPayDate()) : null);
			payable.setCategory(categoryConverter.parseRequestToDomain(dto.getCategory()));
			payable.setNote(dto.getNote());
		}
		
		return payable;
	}

	@Override
	public PayableEntity parseDomainToEntity(Payable domain) {
		PayableEntity entity = null;
		
		if(Objects.nonNull(domain)) {
			entity = new PayableEntity();
			
			entity.setId(new PayableEntityId(domain.getId().getPayableId(), 
					domain.getId().getWorkshopId()));
			entity.setCreationDate(domain.getCreationDate());
			entity.setDescription(domain.getDescription());
			entity.setDocNumber(domain.getDocNumber());
			entity.setDueDate(domain.getDueDate());
			entity.setPayValue(domain.getPayValue());
			entity.setPayDate(domain.getPayDate());
			entity.setCategoryId(domain.getCategory().getId().getCategoryId());
			entity.setNote(domain.getNote());
		}
		
		return entity;
	}

	@Override
	public Payable parseEntityToDomain(PayableEntity entity) {
		Payable payable = null;
		
		if(Objects.nonNull(entity)) {
			payable = new Payable();
			
			payable.setId(new PayableId(entity.getId().getId(), entity.getId().getWorkshopId()));
			payable.setCreationDate(entity.getCreationDate());
			payable.setDescription(entity.getDescription());
			payable.setDocNumber(entity.getDocNumber());
			payable.setDueDate(entity.getDueDate());
			payable.setPayValue(entity.getPayValue());
			payable.setPayDate(entity.getPayDate());
			payable.setCategory(categoryConverter.parseEntityToDomain(entity.getCategory()));
			payable.setNote(entity.getNote());
		}
		
		return payable;
	}

	@Override
	public PayableDTO parseDomainToResponse(Payable domain) {
		PayableDTO dto = null;
		
		if(Objects.nonNull(domain)) {
			dto = new PayableDTO();
			
			dto.setId(domain.getId().getPayableId());
			dto.setWorkshopId(domain.getId().getWorkshopId());
			dto.setCreationDate(Objects.nonNull(domain.getCreationDate()) ?
					domain.getCreationDate().toString() : null);
			dto.setDescription(domain.getDescription());
			dto.setDocNumber(domain.getDocNumber());
			dto.setDueDate(Objects.nonNull(domain.getDueDate()) ?
					domain.getDueDate().toString() : null);
			dto.setPayValue(Objects.nonNull(domain.getPayValue()) ?
					domain.getPayValue().toString() : null);
			dto.setPayDate(Objects.nonNull(domain.getPayDate()) ?
					domain.getPayDate().toString() : null);
			dto.setCategory(categoryConverter.parseDomainToResponse(domain.getCategory()));
			dto.setNote(domain.getNote());
		}
		
		return dto;
	}
}
