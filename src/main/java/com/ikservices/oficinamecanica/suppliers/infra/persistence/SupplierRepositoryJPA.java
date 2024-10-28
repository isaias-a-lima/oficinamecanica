package com.ikservices.oficinamecanica.suppliers.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepositoryJPA extends JpaRepository<SupplierEntity, SupplierEntityId>{
	
	@Query("SELECT s from SupplierEntity s WHERE s.id.workshopId = :workshopId AND s.name LIKE concat('%', :search, '%')")
	public List<SupplierEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId, @Param("search") String search);
	
	@Query("SELECT CASE WHEN MAX(s.id.id) IS NULL THEN 1 ELSE (MAX(s.id.id) + 1) END FROM SupplierEntity s WHERE s.workshopEntity.id = :workshopId")
	public Long getNextSupplierId(@Param("workshopId") Long workshopId);
}
