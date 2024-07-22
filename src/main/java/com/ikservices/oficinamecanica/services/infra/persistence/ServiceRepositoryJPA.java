package com.ikservices.oficinamecanica.services.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepositoryJPA extends JpaRepository<ServiceEntity, ServiceEntityId>{
	
	@Query("SELECT s FROM ServiceEntity s WHERE s.workshopEntity.id = :workshopId")
	public List<ServiceEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId);
}
