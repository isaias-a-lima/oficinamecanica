package com.ikservices.oficinamecanica.vehicles.infra.gateway;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
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
	
	public VehicleRepositoryImpl(VehicleConverter converter, 
			VehicleRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}

	@Override
	public Vehicle saveVehicle(Vehicle vehicle, Long vehicleId) {
		Optional<VehicleEntity> optional = repository.findById(vehicleId);
		
		if(optional.isPresent()) {
			throw new IKException(HttpStatus.FOUND.value(), IKMessageType.WARNING,
					"Veiculo já cadastrado.");
		}
		
		VehicleEntity savedEntity = repository.save(converter.parseEntity(vehicle));
		return converter.parseVehicle(savedEntity);
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
	public Vehicle getVehicle(Long vehicleId) {
		return this.converter.parseVehicle(repository.getById(vehicleId));
	}

	@Override
	public List<Vehicle> listVehicles(IdentificationDocumentVO customerId, Long workshopId) {
		CustomerEntity entity = new CustomerEntity();
		entity.setId(new CustomerEntityId(workshopId, customerId.getDocument()));
		
		return this.converter.parseVehicleList(repository.
				findAllByCustomerEntity(entity));
	}

	@Override
	public void deleteVehicle(VehicleEntity vehicleEntity) {
		vehicleEntity.delete();
	}
}
