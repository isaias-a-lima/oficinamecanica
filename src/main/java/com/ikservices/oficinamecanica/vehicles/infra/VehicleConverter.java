package com.ikservices.oficinamecanica.vehicles.infra;

import java.util.*;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;
import com.ikservices.oficinamecanica.vehicles.application.VehicleException;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleDTO;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleResponse;
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
		entity.setPlate(vehicle.getPlate());
		entity.setActive(vehicle.isActive());
		
		return entity;
	}
	
	public List<Map<Long, Vehicle>> parseVehicleList(List<VehicleEntity> vehicleEntityList) {
		List<Map<Long, Vehicle>> vehicleList = new ArrayList<>();
		
		if(Objects.nonNull(vehicleEntityList) && !vehicleEntityList.isEmpty()) {
			for(VehicleEntity vehicleEntity : vehicleEntityList) {
				Map<Long, Vehicle> vehicleMap = new HashMap<>();
				vehicleMap.put(vehicleEntity.getVehicleId(), this.parseVehicle(vehicleEntity));
				vehicleList.add(vehicleMap);
			}
		}
		
		return vehicleList;
	}
	
	public List<VehicleEntity> parseVehicleEntityList(List<Vehicle> vehicleList) {
		List<VehicleEntity> vehicleEntityList = new ArrayList<>();
		
		if(Objects.nonNull(vehicleList) && !vehicleList.isEmpty()) {
			for(Vehicle vehicle : vehicleList) {
				vehicleEntityList.add(this.parseEntity(vehicle));
			}
		}
		
		return vehicleEntityList;
	}
	
	public Vehicle parseVehicle(VehicleDTO dto) {
		if(Objects.isNull(dto)) {
			throw new VehicleException("Null object");
		}
		
		Vehicle vehicle = new Vehicle();

		if (Objects.nonNull(dto.getCustomer())) {
			Customer customer = new Customer();
			customer.setId(new CustomerId(dto.getCustomer().getWorkshopId(), new IdentificationDocumentVO(dto.getCustomer().getDocId())));
			vehicle.setCustomer(customer);
		}

		vehicle.setBrand(dto.getBrand());
		vehicle.setEngine(dto.getEngine());
		vehicle.setManufacturing(dto.getManufacturing());
		vehicle.setModel(dto.getModel());
		vehicle.setPlate(dto.getPlate());
		vehicle.setObservations(dto.getObservations());
		
		return vehicle;
	}
	
	public VehicleDTO parseDTO(Vehicle vehicle) {
		if(Objects.isNull(vehicle)) {
			throw new VehicleException("Null Object");
		}
		
		VehicleDTO dto = new VehicleDTO();
		
		dto.setBrand(vehicle.getBrand());
		dto.setCustomer(Objects.nonNull(vehicle.getCustomer()) ?
				new CustomerDTO(vehicle.getCustomer()) 
				: null);
		dto.setEngine(vehicle.getEngine());
		dto.setManufacturing(vehicle.getManufacturing());
		dto.setModel(vehicle.getModel());
		dto.setObservations(vehicle.getObservations());
		dto.setPlate(vehicle.getPlate());
	
		return dto;
	}
	
	public List<VehicleResponse> parseResponseList(List<Map<Long, Vehicle>> vehicleList) {
		List<VehicleResponse> vehicleResponseList = new ArrayList<>();
		
		if(Objects.nonNull(vehicleList) && !vehicleList.isEmpty()) {
			for (Map<Long, Vehicle> vehicleMap : vehicleList) {
				for (Long vehicleId : vehicleMap.keySet()) {
					vehicleResponseList.add(new VehicleResponse(vehicleMap.get(vehicleId), vehicleId));
				}
			}
		}
		
		return vehicleResponseList;
	}
	
	public List<VehicleDTO> parseDTOList(List<Vehicle> vehicleList) {
		List<VehicleDTO> vehicleDTOList = new ArrayList<>();
		
		if(Objects.nonNull(vehicleList) && !vehicleList.isEmpty()) {
			for(Vehicle vehicle: vehicleList) {
				vehicleDTOList.add(this.parseDTO(vehicle));
			}
		}
		
		return vehicleDTOList;
	}
}
