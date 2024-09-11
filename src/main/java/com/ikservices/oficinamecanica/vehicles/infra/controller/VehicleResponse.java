package com.ikservices.oficinamecanica.vehicles.infra.controller;

import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VehicleResponse {
	String customerId;
	Long workshopId;
	String plate;
	String brand;
	String model;
	String manufacturing;
	String engine;
	String observations;
	
	public VehicleResponse(Vehicle vehicle) {
		this.customerId = vehicle.getCustomer().getId().getDocId().getDocument();
		this.workshopId = vehicle.getCustomer().getWorkshop().getDocId();
		this.plate = vehicle.getPlate();
		this.brand = vehicle.getBrand();
		this.model = vehicle.getModel();
		this.manufacturing = vehicle.getManufacturing();
		this.engine = vehicle.getEngine();
		this.observations = vehicle.getObservations();
	}
}
