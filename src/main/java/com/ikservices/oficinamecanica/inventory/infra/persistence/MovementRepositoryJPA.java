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
	
	@Query("SELECT MAX(m.movementDate) FROM MovementEntity m WHERE m.partId = :partId AND "
			+ "m.id.workshopId = :workshopId AND m.movementType = 2")
	public LocalDate getLastFinalBalanceDateByPart(@Param("partId") Integer partId, @Param("workshopId") 
	Long workshopId);
	
	@Query("SELECT m.quantity FROM MovementEntity m WHERE m.partId = :partId "
			+ "AND m.id.workshopId = :workshopId AND m.movementType = 2 AND m.movementDate = :basedate")
	public Integer getLastFinalBalanceValueByPart(@Param("partId") Integer partId, 
			@Param("workshopId") Long workshopId, @Param("basedate") LocalDate basedate);
	
	@Query("SELECT COALESCE(SUM(CASE WHEN m.movementType = 1 THEN m.quantity "
			+ "WHEN m.movementType = 0 THEN -m.quantity "
			+ "ELSE 0 END"
			+ "), 0) FROM MovementEntity m WHERE m.partId = :partId AND "
			+ "m.id.workshopId = :workshopId AND m.movementDate > :lastFinalBalanceDate")
	public Integer getMovementQuantityByPart(@Param("partId") Integer partId, 
			@Param("workshopId") Long workshopId, @Param("lastFinalBalanceDate") LocalDate lastFinalBalanceDate);
}
