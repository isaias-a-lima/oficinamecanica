package com.ikservices.oficinamecanica.suppliers.application.usecases;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

public class UpdateSupplier {
	private final SupplierRepository repository;
	
	public UpdateSupplier(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public Supplier execute(Supplier supplier) {
		return repository.updateSupplier(supplier);
	}
}
