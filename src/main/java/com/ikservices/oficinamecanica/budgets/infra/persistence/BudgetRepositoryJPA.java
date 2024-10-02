package com.ikservices.oficinamecanica.budgets.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public interface BudgetRepositoryJPA extends JpaRepository<BudgetEntity, Long>{
	
	public List<BudgetEntity> findAllByVehicleEntity(@Param("vehicleEntity") VehicleEntity vehicleEntity);
}
