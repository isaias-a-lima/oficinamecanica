package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.payables.domain.Category;
import com.ikservices.oficinamecanica.payables.domain.CategoryId;

public class GetCategory {
	private CategoryRepository repository;
	
	public GetCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public Category execute(CategoryId id) {
		return repository.getCategory(id);
	}
}
