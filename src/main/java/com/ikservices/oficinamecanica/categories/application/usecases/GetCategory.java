package com.ikservices.oficinamecanica.categories.application.usecases;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;

public class GetCategory {
	private CategoryRepository repository;
	
	public GetCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public Category execute(CategoryId id) {
		return repository.getCategory(id);
	}
}
