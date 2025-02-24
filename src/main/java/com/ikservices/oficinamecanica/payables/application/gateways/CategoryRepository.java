package com.ikservices.oficinamecanica.payables.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.payables.domain.Category;
import com.ikservices.oficinamecanica.payables.domain.CategoryId;

public interface CategoryRepository {
	List<Category> listCategory(Long workshopId);
	Category getCategory(CategoryId id);
	Category saveCategory(Category category);
	Category updateCategory(Category category);
}
