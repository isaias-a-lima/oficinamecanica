package com.ikservices.oficinamecanica.categories.application.usecases;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.domain.Category;

public class UpdateCategory {
	private CategoryRepository repository;
	
	public UpdateCategory(CategoryRepository repository) {
		repository = repository;
	}
	
	public Category execute(Category category) {
		return repository.updateCategory(category);
	}
}
