package com.ikservices.oficinamecanica.categories.application.usecases;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.domain.Category;

public class SaveCategory {
	private CategoryRepository repository;
	
	public SaveCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public Category execute(Category category) {
		return repository.saveCategory(category);
	}
}
