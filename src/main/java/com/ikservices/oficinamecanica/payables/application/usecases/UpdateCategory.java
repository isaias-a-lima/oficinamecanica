package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.payables.domain.Category;

public class UpdateCategory {
	private CategoryRepository repository;
	
	public UpdateCategory(CategoryRepository repository) {
		repository = repository;
	}
	
	public Category execute(Category category) {
		return repository.updateCategory(category);
	}
}
