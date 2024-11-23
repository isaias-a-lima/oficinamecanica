package com.ikservices.oficinamecanica.vehicles.infra.gateway;

import java.util.*;

import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleRepositoryJPA;

public class VehicleRepositoryImpl implements VehicleRepository {
	@Autowired
	@Lazy
	private VehicleConverter converter;
	private final VehicleRepositoryJPA repository;
	
	public VehicleRepositoryImpl(VehicleRepositoryJPA repository) {
		this.repository = repository;
	}

	@Override
	public Map<Long, Vehicle> saveVehicle(Vehicle vehicle) {
		CustomerEntity ce = new CustomerEntity();
		ce.setId(new CustomerEntityId(vehicle.getCustomer().getId().getWorkshopId(), vehicle.getCustomer().getId().getDocId().getDocument()));

		VehicleEntity entity = new VehicleEntity();
		entity.setWorkshopId(ce.getId().getWorkshopId());
		entity.setIdDoc(ce.getId().getDocId());
		entity.setCustomerEntity(ce);
		entity.setPlate(vehicle.getPlate());
		entity.setBrand(vehicle.getBrand());
		entity.setModel(vehicle.getModel());
		entity.setColor(vehicle.getColor());
		entity.setFuel(vehicle.getFuel());
		entity.setTransmission(vehicle.getTransmission());
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
	public Map<Long, Vehicle> updateVehicle(Long vehicleId, Vehicle vehicle) {
		Optional<VehicleEntity> optional = repository.findById(vehicleId);
		
		VehicleEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseEntity(vehicle, vehicleId));
		}
		
		Map<Long, Vehicle> vehicleMap = new HashMap<>();
		vehicleMap.put(entity.getVehicleId(), converter.parseVehicle(entity));
		return vehicleMap;
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
