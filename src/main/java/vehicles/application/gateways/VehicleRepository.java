package vehicles.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;

import vehicles.domain.Vehicle;

public interface VehicleRepository {
	Vehicle saveVehicle(Vehicle vehicle, Long vehicleId);
	Vehicle updateVehicle(Vehicle vehicle, Long vehicleId);
	Vehicle getVehicle(Long vehicleId);
	List<Vehicle> listVehicles(IdentificationDocumentVO customerId, Long workshopId);
	Vehicle deleteVehicle(Long vehicleId);
}
