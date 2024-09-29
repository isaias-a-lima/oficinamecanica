package com.ikservices.oficinamecanica.suppliers.application.usecases;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;

public class GetSupplier {
	private final SupplierRepository repository;
	
	public GetSupplier(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public Supplier execute(SupplierId supplierId) {
		return repository.getSupplier(supplierId);
	}
}
