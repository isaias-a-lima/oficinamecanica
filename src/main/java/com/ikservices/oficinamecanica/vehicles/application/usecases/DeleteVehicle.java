package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class DeleteVehicle {
	private final VehicleRepository repository;
	
	public DeleteVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle deletevehicle(Long vehicleId) {
		return repository.deleteVehicle(vehicleId);
	}
}
