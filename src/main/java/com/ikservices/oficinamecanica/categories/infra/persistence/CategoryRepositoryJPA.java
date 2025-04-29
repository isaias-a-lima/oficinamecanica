package com.ikservices.oficinamecanica.categories.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepositoryJPA extends JpaRepository<CategoryEntity, CategoryEntityId>{
	@Query("SELECT CASE WHEN MAX(c.id.categoryId) IS NULL THEN 1 "
			+ "ELSE (MAX(c.id.categoryId) + 1) END "
			+ "FROM CategoryEntity c WHERE c.id.workshopId = :workshopId")
	public Integer getNextCategoryId(@Param("workshopId") Long workshopId);
	
	@Query("SELECT c FROM CategoryEntity c WHERE c.id.workshopId = :workshopId")
	public List<CategoryEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId);
}
