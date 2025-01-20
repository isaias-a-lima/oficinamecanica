package com.ikservices.oficinamecanica.budgets.infra.persistence;

import java.util.List;

import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BudgetRepositoryJPA extends JpaRepository<BudgetEntity, Long>{

	@Query("SELECT b FROM BudgetEntity b WHERE b.vehicleId = :vehicleId AND b.budgetStatus = :status")
	public List<BudgetEntity> getBudgetsByVehicle(@Param("vehicleId") Long vehicleId, @Param("status") BudgetStatusEnum status);

	@Query("SELECT b FROM BudgetEntity b WHERE b.vehicle.workshopId = :workshopId AND b.vehicle.idDoc = :idDoc AND b.budgetStatus = :status")
	public List<BudgetEntity> getBudgetsByCustomer(@Param("workshopId") Long workshopId, @Param("idDoc") String idDoc, @Param("status") BudgetStatusEnum status);

	@Query("SELECT b FROM BudgetEntity b WHERE b.vehicle.workshopId = :workshopId AND b.budgetStatus = :status")
	public List<BudgetEntity> getBudgetsByWorkshop(@Param("workshopId") Long workshopId, @Param("status") BudgetStatusEnum status);
}
