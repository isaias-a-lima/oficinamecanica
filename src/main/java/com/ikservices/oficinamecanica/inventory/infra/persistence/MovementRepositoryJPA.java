package com.ikservices.oficinamecanica.inventory.infra.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovementRepositoryJPA extends JpaRepository<MovementEntity, MovementEntityId>{
	
	@Query("SELECT m FROM MovementEntity m WHERE "
			+ "m.id.workshopId = :workshopId "
			+ "AND m.partId = :partId "
			+ "AND ((:movementType = 'CREDIT' AND m.movementType = 1) "
			+ "OR (:movementType = 'DEBIT' AND m.movementType = 2) "
			+ "OR (:movementType = 'NONE')) "
			+ "AND m.movementDate BETWEEN :startDate AND :endDate "
			+ "ORDER BY m.movementDate")
	public List<MovementEntity> listMovementByPartAndType(@Param("partId") Integer partId, 
			@Param("workshopId") Long workshopId, @Param("startDate")  LocalDate startDate, 
			@Param("endDate") LocalDate endDate, @Param("movementType") String movementType);
}
