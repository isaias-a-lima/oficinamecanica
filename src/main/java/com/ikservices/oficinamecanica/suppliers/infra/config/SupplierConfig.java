package com.ikservices.oficinamecanica.suppliers.infra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetNextSupplierId;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.application.usecases.SaveSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.UpdateSupplier;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;
import com.ikservices.oficinamecanica.suppliers.infra.gateway.SupplierRepositoryImpl;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierRepositoryJPA;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

@Configuration
@PropertySource(name="suppliers.properties", value="classpath:suppliers.properties", encoding="UTF-8")
public class SupplierConfig {
	@Autowired
	Environment environment;
	
	@Bean
	public SupplierRepository supplierRepository(SupplierConverter converter, SupplierRepositoryJPA repository) {
		return new SupplierRepositoryImpl(converter, repository);
	}
	
	@Bean
	public SupplierConverter supplierConverter(WorkshopConverter workshopConverter) {
		return new SupplierConverter(workshopConverter);
	}
	
	@Bean
	public GetSupplier getSupplier(SupplierRepository repository) {
		return new GetSupplier(repository);
	}
	
	@Bean
	public SaveSupplier saveSupplier(SupplierRepository repository) {
		return new SaveSupplier(repository);
	}
	
	@Bean
	public UpdateSupplier updateSupplier(SupplierRepository repository) {
		return new UpdateSupplier(repository);
	}
	
	@Bean
	public GetSupplierList getSupplierList(SupplierRepository repository) {
		return new GetSupplierList(repository);
	}
	
	@Bean
	public GetNextSupplierId getNextSupplierId(SupplierRepository repository) {
		return new GetNextSupplierId(repository);
	}
}
