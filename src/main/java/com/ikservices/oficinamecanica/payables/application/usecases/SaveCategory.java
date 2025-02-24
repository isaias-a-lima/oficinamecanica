package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.payables.domain.Category;

public class SaveCategory {
	private CategoryRepository repository;
	
	public SaveCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public Category execute(Category category) {
		return repository.saveCategory(category);
	}
}
