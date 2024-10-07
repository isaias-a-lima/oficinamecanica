package com.ikservices.oficinamecanica.vehicles.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.vehicles.application.gateways.VehicleRepository;
import com.ikservices.oficinamecanica.vehicles.application.usecases.DeleteVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.GetVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.ListVehicles;
import com.ikservices.oficinamecanica.vehicles.application.usecases.SaveVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.UpdateVehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;
import com.ikservices.oficinamecanica.vehicles.infra.gateway.VehicleRepositoryImpl;
import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleRepositoryJPA;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

@Configuration
@PropertySource(name="vehicle.properties", value="classpath:vehicles.properties", encoding="utf-8")
public class VehicleConfig {
	
	@Bean
	public VehicleRepository vehicleRepository(VehicleRepositoryJPA repository) {
		return new VehicleRepositoryImpl(repository);
	}
	
	@Bean
	public GetVehicle getVehicle(VehicleRepository repository) {
		return new GetVehicle(repository);
	}
	
	@Bean
	public UpdateVehicle updateVehicle(VehicleRepository repository) {
		return new UpdateVehicle(repository);
	}
	
	@Bean
	public ListVehicles listVehicles(VehicleRepository repository) {
		return new ListVehicles(repository);
	}
	
	@Bean
	public DeleteVehicle deleteVehicle(VehicleRepository repository) {
		return new DeleteVehicle(repository);
	}
	
	@Bean
	public SaveVehicle saveVehicle(VehicleRepository repository) {
		return new SaveVehicle(repository);
	}
}
