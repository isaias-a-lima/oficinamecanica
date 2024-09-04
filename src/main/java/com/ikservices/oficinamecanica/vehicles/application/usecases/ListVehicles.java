package com.ikservices.oficinamecanica.vehicles.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

public class ListVehicles {
	private final VehicleRepository repository;
	
	public ListVehicles(VehicleRepository repository) {
		this.repository = repository;
	}
	
	public List<Vehicle> execute(IdentificationDocumentVO customerId, Long workshopId) {
		return repository.listVehicles(customerId, workshopId);
	}
}
