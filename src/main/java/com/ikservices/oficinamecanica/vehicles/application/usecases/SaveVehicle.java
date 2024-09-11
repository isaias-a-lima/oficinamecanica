package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class SaveVehicle {
	private final VehicleRepository repository;
	
	public SaveVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Vehicle vehicle) {
		return repository.saveVehicle(vehicle);
	}
}
