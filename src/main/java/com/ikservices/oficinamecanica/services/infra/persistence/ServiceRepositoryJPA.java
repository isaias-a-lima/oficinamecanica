 package com.ikservices.oficinamecanica.services.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRepositoryJPA extends JpaRepository<ServiceEntity, ServiceEntityId>{
	
	@Query("SELECT s FROM ServiceEntity s WHERE s.id.workshopId = :workshopId AND description LIKE concat('%', :search, '%')")
	public List<ServiceEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId, @Param("search") String search);

	@Query("SELECT CASE WHEN MAX(s.id.id) IS NULL THEN 1 ELSE (MAX(s.id.id) + 1) END FROM ServiceEntity s WHERE s.workshopEntity.id = :workshopId")
	public Long getNextServiceId(@Param("workshopId") Long workshopId);
}
