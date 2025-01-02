package com.ikservices.oficinamecanica.budgets.infra.persistence;

import java.util.List;

import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public interface BudgetRepositoryJPA extends JpaRepository<BudgetEntity, Long>{
	
	public List<BudgetEntity> findAllByVehicleEntity(@Param("vehicleEntity") VehicleEntity vehicleEntity);

	@Query("SELECT b FROM BudgetEntity b WHERE b.vehicleEntity.workshopId = :workshopId AND b.vehicleEntity.idDoc = :idDoc AND b.budgetStatus = :status")
	public List<BudgetEntity> getBudgetsByCustomer(@Param("workshopId") Long workshopId, @Param("idDoc") String idDoc, @Param("status") BudgetStatusEnum status);
}
