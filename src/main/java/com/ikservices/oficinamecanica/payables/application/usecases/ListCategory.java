package com.ikservices.oficinamecanica.payables.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.payables.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.payables.domain.Category;

public class ListCategory {
	private CategoryRepository repository;
	
	public ListCategory(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public List<Category> listCategory(Long workshopId) {
		return repository.listCategory(workshopId);
	}
}
