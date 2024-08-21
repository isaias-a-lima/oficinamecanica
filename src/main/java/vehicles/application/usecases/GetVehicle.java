package vehicles.application.usecases;

import vehicles.application.gateways.VehicleRepository;
import vehicles.domain.Vehicle;

public class GetVehicle {
	private final VehicleRepository repository;
	
	public GetVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Long vehicleId) {
		return repository.getVehicle(vehicleId);
	}
}
