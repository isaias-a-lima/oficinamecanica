package com.ikservices.oficinamecanica.vehicles.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;

public interface VehicleRepositoryJPA extends JpaRepository<VehicleEntity, Long> {
	
	
	public List<VehicleEntity> findAllByCustomerEntityAndActiveTrue(@Param("customerEntity") CustomerEntity customerEntity);
	
	public VehicleEntity findByCustomerEntityAndPlate(@Param("customerEntity") CustomerEntity customerEntity, 
			@Param("plate") String plate);
}
