package com.ikservices.oficinamecanica.suppliers.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.suppliers.application.SupplierException;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierEntity;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierEntityId;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;

public class SupplierConverter {
	private final WorkshopConverter workshopConverter;
	
	public SupplierConverter(WorkshopConverter workshopConverter) {
		this.workshopConverter = workshopConverter;
	}
	
	public Supplier parseSupplier(SupplierEntity entity) {
		if(Objects.isNull(entity)) {
			throw new SupplierException("Null object");
		}
		
		Supplier supplier = new Supplier();
		supplier.setSupplierId(new SupplierId(entity.getId().getId(), entity.getId().getWorkshopId()));
		supplier.setName(entity.getName());
		supplier.setLandline(entity.getLandLine());
		supplier.setMobilePhone(entity.getMobilePhone());
		supplier.setEmail(entity.getEmail());
		supplier.setPostalCode(entity.getPostalCode());
		supplier.setAddress(entity.getAddress());
		supplier.setCity(entity.getCity());
		supplier.setState(entity.getState());
		
		return supplier;
	}
	
	public SupplierEntity parseEntity(Supplier supplier) {
		if(Objects.isNull(supplier)) {
			throw new SupplierException("Null object");
		}
		
		SupplierEntity entity = new SupplierEntity();
		entity.setId(new SupplierEntityId(supplier.getSupplierId().getId(), 
				supplier.getSupplierId().getWorkshopid()));
		entity.setWorkshopEntity(Objects.nonNull(supplier.getWorkshop()) ? workshopConverter.
				parseWorkshopEntity(supplier.getWorkshop(), supplier.getSupplierId().getWorkshopid()) : null);
		entity.setName(supplier.getName());
		entity.setLandLine(supplier.getLandline());
		entity.setMobilePhone(supplier.getMobilePhone());
		entity.setEmail(supplier.getEmail());
		entity.setAddress(supplier.getAddress());
		entity.setCity(supplier.getCity());
		entity.setState(supplier.getState());
		
		return entity;
	}
	
	public List<Supplier> parseSupplierList(List<SupplierEntity> supplierEntityList) {
		List<Supplier> supplierList = new ArrayList<>();
		
		if(Objects.nonNull(supplierList) && supplierList.isEmpty()) {
			for(SupplierEntity entity : supplierEntityList) {
				supplierList.add(this.parseSupplier(entity));
			}
		}
		
		return supplierList;
	}
}
