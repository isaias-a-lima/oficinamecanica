package com.ikservices.oficinamecanica.categories.infra.gateways;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;
import com.ikservices.oficinamecanica.categories.infra.CategoryConverter;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryEntity;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryEntityId;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryRepositoryJPA;
import com.ikservices.oficinamecanica.commons.exception.IKException;

public class CategoryRepositoryImpl implements CategoryRepository {

	private final CategoryConverter converter;
	private final CategoryRepositoryJPA repositoryJPA;
	
	@Autowired
	private Environment environment;
	
	public CategoryRepositoryImpl(CategoryConverter converter, CategoryRepositoryJPA repositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}
	
	@Override
	public List<Category> listCategory(Long workshopId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.findAllByWorkshopId(workshopId));
	}

	@Override
	public Category getCategory(CategoryId id) {
		return this.converter.parseEntityToDomain(repositoryJPA.getById(new CategoryEntityId(id.getCategoryId(), 
				id.getWorkshopId())));
	}

	@Override
	public Category saveCategory(Category category) {
		Optional<CategoryEntity> optional = repositoryJPA.
				findById(new CategoryEntityId(category.getId().getCategoryId(),
						category.getId().getWorkshopId()));
		
		if(optional.isPresent()) {
			throw new IKException("Category is already present.");
		}
		
		CategoryEntity savedEntity = repositoryJPA.save(converter.parseDomainToEntity(category));
		return converter.parseEntityToDomain(savedEntity);
	}

	@Override
	public Category updateCategory(Category category) {
		Optional<CategoryEntity> optional = repositoryJPA.
				findById(new CategoryEntityId(category.getId().getCategoryId(),
						category.getId().getWorkshopId()));
		
		CategoryEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseDomainToEntity(category));
		}
		
		return converter.parseEntityToDomain(entity);
	}

	@Override
	public Integer getNextCategoryId(Long workshopId) {
		return repositoryJPA.getNextCategoryId(workshopId);
	}

}
