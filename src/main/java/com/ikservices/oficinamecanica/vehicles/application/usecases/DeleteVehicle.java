package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public class DeleteVehicle {
	private final VehicleRepository repository;
	
	public DeleteVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public void deletevehicle(VehicleEntity vehicleEntity) {
		repository.deleteVehicle(vehicleEntity);
	}
}
