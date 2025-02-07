package com.ikservices.oficinamecanica.vehicles.infra;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.utils.StringUtil;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;
import com.ikservices.oficinamecanica.vehicles.application.VehicleException;
import com.ikservices.oficinamecanica.vehicles.domain.FuelEnum;
import com.ikservices.oficinamecanica.vehicles.domain.TransmissionEnum;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleDTO;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleResponse;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VehicleConverter {
	@Autowired
	@Lazy
	private CustomerConverter customerConverter;
	
	public Vehicle parseVehicle(VehicleEntity entity) {
		if(Objects.isNull(entity)) {
			throw new VehicleException("Null object");
		}
		
		Vehicle vehicle = new Vehicle();

		vehicle.setCustomer(customerConverter.parseCustomer(entity.getCustomerEntity(), true));
		vehicle.setPlate(StringUtil.validString(entity.getPlate()));
		vehicle.setManufacturing(StringUtil.validString(entity.getManufacturing()));
		vehicle.setObservations(StringUtil.validString(entity.getObservations()));
		vehicle.setBrand(StringUtil.validString(entity.getBrand()));
		vehicle.setEngine(StringUtil.validString(entity.getEngine()));
		vehicle.setModel(StringUtil.validString(entity.getModel()));
		vehicle.setColor(StringUtil.validString(entity.getColor()));
		vehicle.setFuel(entity.getFuel());
		vehicle.setTransmission(entity.getTransmission());
		vehicle.setActive(entity.getActive());
		
		return vehicle;
	}
	
	public VehicleEntity parseEntity(Vehicle vehicle, Long vehicleId) {
		if(Objects.isNull(vehicle)) {
			throw new VehicleException("Null object");
		}
		
		VehicleEntity entity = new VehicleEntity();
		entity.setVehicleId(Objects.nonNull(vehicleId) ? vehicleId : null);
		entity.setIdDoc(Objects.nonNull(vehicle.getCustomer()) ? vehicle.getCustomer().getId().getDocId().getDocument() : null);
		entity.setWorkshopId(Objects.nonNull(vehicle.getCustomer()) ? vehicle.getCustomer().getId().getWorkshopId() : null);
		entity.setCustomerEntity(Objects.nonNull(vehicle.getCustomer()) ? customerConverter.parseEntity(vehicle.getCustomer()) : null);
		entity.setEngine(vehicle.getEngine());
		entity.setManufacturing(vehicle.getManufacturing());
		entity.setModel(vehicle.getModel());
		entity.setColor(vehicle.getColor());
		entity.setFuel(vehicle.getFuel());
		entity.setTransmission(vehicle.getTransmission());
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
	
	public List<VehicleEntity> parseVehicleEntityList(List<Map<Long, Vehicle>> vehicleList) {
		List<VehicleEntity> vehicleEntityList = new ArrayList<>();
		
		if(Objects.nonNull(vehicleList) && !vehicleList.isEmpty()) {

			for(Map<Long, Vehicle> vehicleMap : vehicleList) {

				for (Map.Entry<Long, Vehicle> entry : vehicleMap.entrySet()) {

					VehicleEntity vehicleEntity = this.parseEntity(entry.getValue(), entry.getKey());
					vehicleEntity.setVehicleId(entry.getKey());

					vehicleEntityList.add(vehicleEntity);
				}
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
		vehicle.setColor(dto.getColor());
		vehicle.setFuel(FuelEnum.getByIndex(dto.getFuel()));
		vehicle.setTransmission(TransmissionEnum.getByIndex(dto.getTransmission()));
		vehicle.setPlate(dto.getPlate());
		vehicle.setObservations(dto.getObservations());
		
		return vehicle;
	}

	public Vehicle parseVehicle(VehicleResponse dto) {
		if(Objects.isNull(dto)) {
			throw new VehicleException("Null object");
		}

		Vehicle vehicle = new Vehicle();

		if (!dto.getCustomerId().isEmpty() && Objects.nonNull(dto.getWorkshopId())) {
			Customer customer = new Customer();
			customer.setId(new CustomerId(dto.getWorkshopId(), new IdentificationDocumentVO(dto.getCustomerId())));
			vehicle.setCustomer(customer);
		}

		vehicle.setBrand(dto.getBrand());
		vehicle.setEngine(dto.getEngine());
		vehicle.setManufacturing(dto.getManufacturing());
		vehicle.setModel(dto.getModel());
		vehicle.setColor(dto.getColor());
		vehicle.setFuel(FuelEnum.getByIndex(dto.getFuel()));
		vehicle.setTransmission(TransmissionEnum.getByIndex(dto.getTransmission()));
		vehicle.setPlate(dto.getPlate());
		vehicle.setObservations(dto.getObservations());

		return vehicle;
	}
	
	public VehicleDTO parseDTO(Map<Long, Vehicle> vehicleMap) {
		if(Objects.isNull(vehicleMap)) {
			throw new VehicleException("Null Object");
		}

		for (Long vehicleId : vehicleMap.keySet()) {
			VehicleDTO dto = new VehicleDTO();

			Vehicle vehicle = vehicleMap.get(vehicleId);

			dto.setVehicleId(vehicleId);
			dto.setBrand(vehicle.getBrand());
			dto.setCustomer(Objects.nonNull(vehicle.getCustomer()) ?
					customerConverter.parseCustomerDTO(vehicle.getCustomer(), true)
					: null);
			dto.setEngine(vehicle.getEngine());
			dto.setManufacturing(vehicle.getManufacturing());
			dto.setModel(vehicle.getModel());
			dto.setColor(vehicle.getColor());
			dto.setFuel(Objects.nonNull(vehicle.getFuel()) ? vehicle.getFuel().ordinal(): null);
			dto.setTransmission(Objects.nonNull(vehicle.getTransmission()) ? vehicle.getTransmission().ordinal() : null);
			dto.setObservations(vehicle.getObservations());
			dto.setPlate(vehicle.getPlate());
			return dto;
		}

		return null;
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

	//TODO fix this method
	public List<VehicleDTO> parseDTOList(List<Vehicle> vehicleList) {
		List<VehicleDTO> vehicleDTOList = new ArrayList<>();
		

		
		return vehicleDTOList;
	}
}
