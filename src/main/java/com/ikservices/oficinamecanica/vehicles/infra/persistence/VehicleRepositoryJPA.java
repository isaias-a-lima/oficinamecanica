package com.ikservices.oficinamecanica.vehicles.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleRepositoryJPA extends JpaRepository<VehicleEntity, Long> {
	
	
	public List<VehicleEntity> findAllByCustomer(@Param("customerId") Long customerId);
}
