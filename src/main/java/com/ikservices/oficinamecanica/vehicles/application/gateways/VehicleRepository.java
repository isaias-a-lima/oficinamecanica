package com.ikservices.oficinamecanica.vehicles.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public interface VehicleRepository {
	Vehicle saveVehicle(Vehicle vehicle);
	Vehicle updateVehicle(Vehicle vehicle, Long vehicleId);
	Vehicle getVehicle(Long vehicleId);
	List<Vehicle> listVehicles(IdentificationDocumentVO customerId, Long workshopId);
	void deleteVehicle(VehicleEntity vehicleEntity);
}
