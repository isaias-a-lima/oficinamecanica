package com.ikservices.oficinamecanica.vehicles.infra.gateway;

import java.util.*;

import org.springframework.http.HttpStatus;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleRepositoryJPA;

public class VehicleRepositoryImpl implements VehicleRepository {
	
	private final VehicleConverter converter;
	private final VehicleRepositoryJPA repository;
	private final CustomerConverter customerConverter;
	
	public VehicleRepositoryImpl(VehicleConverter converter, 
			VehicleRepositoryJPA repository, CustomerConverter customerConverter) {
		this.converter = converter;
		this.repository = repository;
		this.customerConverter = customerConverter;
	}

	@Override
	public Map<Long, Vehicle> saveVehicle(Vehicle vehicle) {
		CustomerEntity ce = new CustomerEntity();
		ce.setId(new CustomerEntityId(vehicle.getCustomer().getId().getWorkshopId(), vehicle.getCustomer().getId().getDocId().getDocument()));

		VehicleEntity entity = new VehicleEntity();
		entity.setCustomerEntity(ce);
		entity.setPlate(vehicle.getPlate());
		entity.setBrand(vehicle.getBrand());
		entity.setModel(vehicle.getModel());
		entity.setManufacturing(vehicle.getManufacturing());
		entity.setEngine(vehicle.getEngine());
		entity.setObservations(vehicle.getObservations());
		entity.setActive(true);

		VehicleEntity savedEntity = repository.save(entity);

		Map<Long, Vehicle> vehicleMap = new HashMap<>();
		vehicleMap.put(savedEntity.getVehicleId(), converter.parseVehicle(savedEntity));
		return vehicleMap;
	}

	@Override
	public Vehicle updateVehicle(Vehicle vehicle, Long vehicleId) {
		Optional<VehicleEntity> optional = repository.findById(vehicleId);
		
		VehicleEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseEntity(vehicle));
		}
		
		return converter.parseVehicle(entity);
	}

	@Override
	public Map<Long, Vehicle> getVehicle(Long vehicleId) {
		VehicleEntity entity = repository.getById(vehicleId);
		Vehicle vehicle = this.converter.parseVehicle(entity);
		Map<Long, Vehicle> vehicleMap = new HashMap<>();
		vehicleMap.put(entity.getVehicleId(), vehicle);
		return vehicleMap;
	}

	@Override
	public List<Map<Long, Vehicle>> listVehicles(IdentificationDocumentVO customerId, Long workshopId) {
		CustomerEntity entity = new CustomerEntity();
		entity.setId(new CustomerEntityId(workshopId, customerId.getDocument()));
		
		return this.converter.parseVehicleList(repository.
				findAllByCustomerEntityAndActiveTrue(entity));
	}

	@Override
	public void deleteVehicle(Long vehicleId) {
		Optional<VehicleEntity> vehicleOptional = repository.findById(vehicleId);
		
		if(Objects.isNull(vehicleOptional)) {
			throw new IKException("Veículo não encontrado.");
		}
		
		VehicleEntity vehicleEntity = vehicleOptional.get();
		vehicleEntity.delete();
	}
}
