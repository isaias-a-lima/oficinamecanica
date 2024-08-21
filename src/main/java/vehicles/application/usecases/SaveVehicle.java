package vehicles.application.usecases;

import vehicles.application.gateways.VehicleRepository;
import vehicles.domain.Vehicle;

public class SaveVehicle {
	private final VehicleRepository repository;
	
	public SaveVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle execute(Vehicle vehicle, Long vehicleId) {
		return repository.saveVehicle(vehicle, vehicleId);
	}
}
