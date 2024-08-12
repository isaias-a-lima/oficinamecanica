package com.ikservices.oficinamecanica.parts.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PartRepositoryJPA extends JpaRepository<PartEntity, PartEntityId>{

	@Query("SELECT p FROM PartEntity p WHERE p.workshopEntity.id = :workshopId")
	public List<PartEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId);
	
	@Query("SELECT CASE WHEN MAX(p.id.id) IS NULL THEN 1 ELSE (MAX(p.id.id) + 1) END FROM PartEntity p WHERE p.workshopEntity.id = :workshopId")
	public Long getNextPartId(@Param("workshopId") Long workshopId);
}