package com.ikservices.oficinamecanica.vehicles.application.usecases;

import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

import java.util.Map;

public class GetVehicle {
	private final VehicleRepository repository;
	
	public GetVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Map<Long, Vehicle> execute(Long vehicleId) {
		return repository.getVehicle(vehicleId);
	}
}
