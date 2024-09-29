package com.ikservices.oficinamecanica.vehicles.application.usecases;

import java.util.Map;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class UpdateVehicle {
	private final VehicleRepository repository;
	
	public UpdateVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Vehicle> execute(Long vehicleId, Vehicle vehicle) {
		return repository.updateVehicle(vehicleId, vehicle);
	}
}
