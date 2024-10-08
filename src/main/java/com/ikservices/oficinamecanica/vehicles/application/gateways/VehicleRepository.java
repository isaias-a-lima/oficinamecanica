package com.ikservices.oficinamecanica.vehicles.application.gateways;

import java.util.List;
import java.util.Map;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

public interface VehicleRepository {
	Map<Long, Vehicle> saveVehicle(Vehicle vehicle);
	Map<Long, Vehicle> updateVehicle(Long vehicleId, Vehicle vehicle);
	Map<Long, Vehicle> getVehicle(Long vehicleId);
	List<Map<Long, Vehicle>> listVehicles(IdentificationDocumentVO customerId, Long workshopId);
	void deleteVehicle(Long vehicleId);
}
