package com.ikservices.oficinamecanica.suppliers.infra.gateway;

import java.util.List;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier updateSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier getSupplier(Long workshopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getSupplierList(Long workshopId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextSupplierId(Long workshopId) {
		// TODO Auto-generated method stub
		return null;
	}
}
