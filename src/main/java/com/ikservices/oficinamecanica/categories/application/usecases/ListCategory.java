package com.ikservices.oficinamecanica.categories.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.domain.Category;

public class ListCategory {
	private CategoryRepository repository;
	
	public ListCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public List<Category> execute(Long workshopId) {
		return repository.listCategory(workshopId);
	}
}
