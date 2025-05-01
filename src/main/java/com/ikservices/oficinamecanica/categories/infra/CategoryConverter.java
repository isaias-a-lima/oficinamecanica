package com.ikservices.oficinamecanica.categories.infra;

import java.util.Objects;

import com.ikservices.oficinamecanica.categories.domain.CategoryTypeEnum;
import com.ikservices.oficinamecanica.commons.utils.DateUtil;
import org.springframework.stereotype.Component;

import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;
import com.ikservices.oficinamecanica.categories.infra.controller.CategoryDTO;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryEntity;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryEntityId;
import com.ikservices.oficinamecanica.commons.generics.IKConverter;

@Component
public class CategoryConverter extends IKConverter<CategoryDTO, Category, CategoryEntity, CategoryDTO>{

	@Override
	public Category parseRequestToDomain(CategoryDTO request) {
		Category domain = null;
		
		if(Objects.nonNull(request)) {
			domain = new Category();
			
			domain.setId(new CategoryId(request.getCategoryId(), request.getWorkshopId()));
			domain.setCreationDate(DateUtil.parseToLocalDate(request.getCreationDate()));
			domain.setDescription(request.getDescription());
			domain.setCategType(CategoryTypeEnum.findByIndex(request.getCategoryType()));
			domain.setActivated(request.isActivated());
		}
		
		return domain;
	}

	@Override
	public CategoryEntity parseDomainToEntity(Category domain) {
		CategoryEntity entity = null;
		
		if(Objects.nonNull(domain)) {
			entity = new CategoryEntity();
			
			entity.setId(new CategoryEntityId(domain.getId().getCategoryId(), domain.getId().getWorkshopId()));
			entity.setCreation(domain.getCreationDate());
			entity.setDescription(domain.getDescription());
			entity.setCategType(domain.getCategType());
			entity.setActivated(domain.getActivated());
		}
		
		return entity;
	}

	@Override
	public Category parseEntityToDomain(CategoryEntity entity) {
		Category domain = null;
		
		if(Objects.nonNull(entity)) {
			domain = new Category();
			
			domain.setId(new CategoryId(entity.getId().getCategoryId(), entity.getId().getWorkshopId()));
			domain.setCreationDate(entity.getCreation());
			domain.setDescription(entity.getDescription());
			domain.setCategType(entity.getCategType());
			domain.setActivated(entity.getActivated());
		}
		
		return domain;
	}

	@Override
	public CategoryDTO parseDomainToResponse(Category domain) {
		CategoryDTO dto = null;
		
		if(Objects.nonNull(domain)) {
			dto = new CategoryDTO();
			
			dto.setCategoryId(domain.getId().getCategoryId());
			dto.setWorkshopId(domain.getId().getWorkshopId());
			dto.setCreationDate(DateUtil.parseToString(domain.getCreationDate()));
			dto.setDescription(domain.getDescription());
			dto.setCategoryType(Objects.nonNull(domain.getCategType()) ? domain.getCategType().ordinal(): null);
			dto.setActivated(domain.getActivated());
		}
		
		return dto;
	}
}
