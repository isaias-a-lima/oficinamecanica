package com.ikservices.oficinamecanica.suppliers.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
import com.ikservices.oficinamecanica.suppliers.application.SupplierException;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.controller.SupplierDTO;
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
		supplier.setIdDoc(new IdentificationDocumentVO(entity.getIdDoc()));
		supplier.setName(entity.getName());
		supplier.setLandline(PhoneVO.parsePhoneVO(entity.getLandLine()));
		supplier.setMobilePhone(PhoneVO.parsePhoneVO(entity.getMobilePhone()));
		supplier.setEmail(new EmailVO(entity.getEmail()));
		
		AddressVO address = new AddressVO();
		
		address.setPostalCode(entity.getPostalCode());
		address.setCity(entity.getCity());
		address.setState(entity.getState());
		address.setStreet(entity.getAddress());
		supplier.setAddress(address);
		
		supplier.setType(TaxPayerEnum.getByType(entity.getType()));
		
		return supplier;
	}
	
	public SupplierEntity parseEntity(Supplier supplier) {
		if(Objects.isNull(supplier)) {
			throw new SupplierException("Null object");
		}
		
		SupplierEntity entity = new SupplierEntity();
		entity.setId(new SupplierEntityId(supplier.getSupplierId().getId(), 
				supplier.getSupplierId().getWorkshopid()));
		entity.setIdDoc(supplier.getIdDoc().getDocument());
		entity.setWorkshopEntity(Objects.nonNull(supplier.getWorkshop()) ? workshopConverter.
				parseWorkshopEntity(supplier.getWorkshop(), supplier.getSupplierId().getWorkshopid()) : null);
		entity.setName(supplier.getName());
		entity.setLandLine(supplier.getLandline().getFullPhone());
		entity.setMobilePhone(supplier.getMobilePhone().getFullPhone());
		entity.setEmail(supplier.getEmail().getMailAddress());
		entity.setAddress(supplier.getAddress().getStreet());
		entity.setPostalCode(supplier.getAddress().getPostalCode());
		entity.setCity(supplier.getAddress().getCity());
		entity.setState(supplier.getAddress().getState());
		entity.setType(supplier.getType().getType());
		
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
	
	public List<SupplierEntity> parseSupplierEntityList(List<Supplier> supplierList) {
		List<SupplierEntity> supplierEntityList = new ArrayList<>();
		
		if(Objects.nonNull(supplierEntityList) && supplierEntityList.isEmpty()) {
			for(Supplier supplier : supplierList) {
				supplierEntityList.add(this.parseEntity(supplier));
			}
		}
		
		return supplierEntityList;
	}
	
	public Supplier parseSupplier(SupplierDTO dto) {
		if(Objects.isNull(dto)) {
			throw new SupplierException("Null object");
		}
		
		Supplier supplier = new Supplier();
		
		supplier.setSupplierId(new SupplierId(dto.getSupplierId(), dto.getWorkshopId()));
		supplier.setIdDoc(new IdentificationDocumentVO(dto.getIdDoc()));
		supplier.setName(dto.getName());
		supplier.setLandline(PhoneVO.parsePhoneVO(dto.getLandLine()));
		supplier.setMobilePhone(PhoneVO.parsePhoneVO(dto.getMobilePhone()));
		supplier.setEmail(new EmailVO(dto.getEmail()));
		
		AddressVO address = new AddressVO();
		
		address.setPostalCode(dto.getPostalCode());
		address.setCity(dto.getCity());
		address.setState(dto.getState());
		address.setStreet(dto.getAddress());
		supplier.setAddress(address);
		
		supplier.setType(TaxPayerEnum.getByType(dto.getType()));
		
		return supplier;
	}
	
	public SupplierDTO parseDTO(Supplier supplier) {
		if(Objects.isNull(supplier)) {
			throw new SupplierException("Null Object");
		}
		
		SupplierDTO dto = new SupplierDTO();
		
		dto.setSupplierId(supplier.getSupplierId().getId());
		dto.setWorkshopId(supplier.getSupplierId().getWorkshopid());
		dto.setIdDoc(supplier.getIdDoc().getFullDocument());
		dto.setName(supplier.getName());
		dto.setLandLine(supplier.getLandline().getFullPhone());
		dto.setMobilePhone(supplier.getMobilePhone().getFullPhone());
		dto.setEmail(supplier.getEmail().getMailAddress());
		dto.setAddress(supplier.getAddress().getStreet());
		dto.setPostalCode(supplier.getAddress().getFormattedPostalCode());
		dto.setCity(supplier.getAddress().getCity());
		dto.setState(supplier.getAddress().getState());
		dto.setType(supplier.getType().getType());
		
		return dto;
	}
	
	public List<SupplierDTO> parseDTOList(List<Supplier> supplierList) {
		List<SupplierDTO> supplierDTOList = new ArrayList<>();
		
		if(Objects.nonNull(supplierList) && !supplierList.isEmpty()) {
			for(Supplier supplier : supplierList) {
				supplierDTOList.add(this.parseDTO(supplier));
			}
		}
			
		return supplierDTOList;
	}
}
