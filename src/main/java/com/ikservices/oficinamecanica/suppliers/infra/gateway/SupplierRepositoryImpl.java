package com.ikservices.oficinamecanica.suppliers.infra.gateway;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.suppliers.infra.constants.SupplierConstants;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierEntity;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierEntityId;
import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierRepositoryJPA;

public class SupplierRepositoryImpl implements SupplierRepository {
	
	private final SupplierConverter converter;
	private final SupplierRepositoryJPA repository;
	
	public SupplierRepositoryImpl(SupplierConverter converter, SupplierRepositoryJPA repository) {
		this.converter = converter;
		this.repository = repository;
	}

	@Override
	public Supplier saveSupplier(Supplier supplier) {
		Optional<SupplierEntity> optional = repository.findById(new SupplierEntityId(
				supplier.getSupplierId().getId(), 
				supplier.getSupplierId().getWorkshopid()));
		
		if(optional.isPresent()) {
			throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), SupplierConstants.SUPPLIER_SAVE_ALREADY_RECORDED_MESSAGE));
		}
		
		SupplierEntity savedEntity = repository.save(converter.parseEntity(supplier));
		return converter.parseSupplier(savedEntity);
	}

	@Override
	public Supplier updateSupplier(Supplier supplier) {
		Optional<SupplierEntity> optional = repository.
				findById(new SupplierEntityId(supplier.getSupplierId().getId(), 
						supplier.getSupplierId().getWorkshopid()));
		
		SupplierEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseEntity(supplier));
		}
		
		return converter.parseSupplier(entity);
	}

	@Override
	public Supplier getSupplier(SupplierId supplierId) {
		return this.converter.parseSupplier(repository.
				getById(new SupplierEntityId(supplierId.getId(), supplierId.getWorkshopid())));
	}

	@Override
	public List<Supplier> getSupplierList(Long workshopId, String search) {
		return this.converter.parseSupplierList(repository.findAllByWorkshopId(workshopId, search));
	}

	@Override
	public Long getNextSupplierId(Long workshopId) {
		return repository.getNextSupplierId(workshopId);
	}
}
