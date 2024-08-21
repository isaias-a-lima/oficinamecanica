package vehicles.application.usecases;

import vehicles.application.gateways.VehicleRepository;
import vehicles.domain.Vehicle;

public class DeleteVehicle {
	private final VehicleRepository repository;
	
	public DeleteVehicle(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public Vehicle deletevehicle(Long vehicleId) {
		return repository.deleteVehicle(vehicleId);
	}
}
