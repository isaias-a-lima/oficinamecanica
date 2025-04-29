	package com.ikservices.oficinamecanica.payables.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayableRepositoryJPA extends JpaRepository<PayableEntity, PayableEntityId>{
	@Query("SELECT CASE WHEN MAX(p.id.id) IS NULL THEN 1 "
			+ "ELSE (MAX(p.id.id) + 1) END"
			+ " FROM PayableEntity p WHERE p.id.workshopId = :workshopId")
	public Integer getNextPayableId(@Param("workshopId") Long workshopId);
	
	@Query("SELECT p FROM PayableEntity p WHERE p.id.workshopId = :workshopId")
	public List<PayableEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId);
}
