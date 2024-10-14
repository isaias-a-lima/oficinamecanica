package com.ikservices.oficinamecanica.vehicles.infra.controller;

import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;

import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VehicleDTO implements Serializable {
	Long vehicleId;
	CustomerDTO customer;
	String plate;
	String brand;
	String model;
	String color;
	String fuel;
	String transmission;
	String manufacturing;
	String engine;
	String observations;

	public VehicleDTO(Vehicle vehicle, Long vehicleId) {
		this.vehicleId = vehicleId;
		this.plate = vehicle.getPlate();
		this.brand = vehicle.getBrand();
		this.model = vehicle.getModel();
		this.color = vehicle.getColor();
		this.fuel = vehicle.getFuel();
		this.transmission = vehicle.getTransmission();
		this.manufacturing = vehicle.getManufacturing();
		this.engine = vehicle.getEngine();
		this.observations = vehicle.getObservations();
	}

	public VehicleDTO(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
}
