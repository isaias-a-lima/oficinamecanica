package vehicles.application.usecases;

import vehicles.application.gateways.VehicleRepository;
import vehicles.domain.Vehicle;

public class UpdateVehicle {
	private final VehicleRepository repository;
	
	public UpdateVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Vehicle vehicle, Long vehicleId) {
		return repository.updateVehicle(vehicle, vehicleId);
	}
}
