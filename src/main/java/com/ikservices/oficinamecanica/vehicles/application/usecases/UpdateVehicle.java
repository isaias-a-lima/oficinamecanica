package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class UpdateVehicle {
	private final VehicleRepository repository;
	
	public UpdateVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Vehicle vehicle, Long vehicleId) {
		return repository.updateVehicle(vehicle, vehicleId);
	}
}
