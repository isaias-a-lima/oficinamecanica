package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class GetVehicle {
	private final VehicleRepository repository;
	
	public GetVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Long vehicleId) {
		return repository.getVehicle(vehicleId);
	}
}
