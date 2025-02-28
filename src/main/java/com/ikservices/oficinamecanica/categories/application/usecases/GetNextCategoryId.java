package com.ikservices.oficinamecanica.categories.application.usecases;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;

public class GetNextCategoryId {
	private CategoryRepository repository;
	
	public GetNextCategoryId(CategoryRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Long workshopId) {
		return repository.getNextCategoryId(workshopId);
	}
}
