package com.ikservices.oficinamecanica.vehicles.infra;

import java.util.Objects;

import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.vehicles.application.VehicleException;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

public class VehicleConverter {
	private final WorkshopConverter workshopConverter;
	private final CustomerConverter customerConverter;
	
	public VehicleConverter(WorkshopConverter workshopConverter, 
			CustomerConverter customerConverter) {
		this.workshopConverter = workshopConverter;
		this.customerConverter = customerConverter;
	}
	
	public Vehicle parseVehicle(VehicleEntity entity) {
		if(Objects.isNull(entity)) {
			throw new VehicleException("Null object");
		}
		
		Vehicle vehicle = new Vehicle();
		vehicle.setCustomer(customerConverter.parseCustomer(entity.getCustomerEntity()));
		vehicle.setPlate(entity.getPlate());
		vehicle.setManufacturing(entity.getManufacturing());
		vehicle.setObservations(entity.getObservations());
		vehicle.setBrand(entity.getBrand());
		vehicle.setEngine(entity.getEngine());
		vehicle.setModel(entity.getModel());
		vehicle.setActive(entity.getActive());
		
		return vehicle;
	}
	
	public VehicleEntity parseEntity(Vehicle vehicle) {
		if(Objects.isNull(vehicle)) {
			throw new VehicleException("Null object");
		}
		
		VehicleEntity entity = new VehicleEntity();
		entity.setCustomerEntity(customerConverter.parseEntity(vehicle.getCustomer()));
		entity.setEngine(vehicle.getEngine());
		entity.setManufacturing(vehicle.getManufacturing());
		entity.setModel(vehicle.getModel());
		entity.setObservations(vehicle.getObservations());
		entity.setBrand(vehicle.getBrand());
		entity.setPlate(entity.getPlate());
		entity.setActive(vehicle.isActive());
		
		return entity;
	}
}
