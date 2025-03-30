package com.ikservices.oficinamecanica.categories.infra.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.categories.application.gateways.CategoryRepository;
import com.ikservices.oficinamecanica.categories.application.usecases.GetCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.GetNextCategoryId;
import com.ikservices.oficinamecanica.categories.application.usecases.ListCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.SaveCategory;
import com.ikservices.oficinamecanica.categories.application.usecases.UpdateCategory;
import com.ikservices.oficinamecanica.categories.infra.CategoryConverter;
import com.ikservices.oficinamecanica.categories.infra.gateways.CategoryRepositoryImpl;
import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryRepositoryJPA;

@Configuration
@PropertySource(name="categories.properties", value="classpath:categories.properties")
public class CategoryConfig {
	@Autowired
	Environment environment;
	
	@Bean
	public CategoryRepository categoryRepository(CategoryConverter converter, 
			CategoryRepositoryJPA repositoryJPA) {
		return new CategoryRepositoryImpl(converter, repositoryJPA);
	}
	
	@Bean
	public GetCategory getCategory(CategoryRepository repository) {
		return new GetCategory(repository);
	}
	
	@Bean
	public ListCategory listCategory(CategoryRepository repository) {
		return new ListCategory(repository);
	}
	
	@Bean
	public SaveCategory saveCategory(CategoryRepository repository) {
		return new SaveCategory(repository);
	}
	
	@Bean
	public UpdateCategory updateCategory(CategoryRepository repository) {
		return new UpdateCategory(repository);
	}
	
	@Bean
	public GetNextCategoryId getNextCategoryId(CategoryRepository repository) {
		return new GetNextCategoryId(repository);
	}
}
