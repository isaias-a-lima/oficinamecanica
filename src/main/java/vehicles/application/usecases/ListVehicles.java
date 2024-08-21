package vehicles.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;

import vehicles.application.gateways.VehicleRepository;
import vehicles.domain.Vehicle;

public class ListVehicles {
	private final VehicleRepository repository;
	
	public ListVehicles(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public List<Vehicle> execute(IdentificationDocumentVO customerId, Long workshopId) {
		return repository.listVehicles(customerId, workshopId);
	}
}
