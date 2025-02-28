package com.ikservices.oficinamecanica.categories.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;

public interface CategoryRepository {
	List<Category> listCategory(Long workshopId);
	Category getCategory(CategoryId id);
	Category saveCategory(Category category);
	Category updateCategory(Category category);
	Integer getNextCategoryId(Long workshopId);
}
