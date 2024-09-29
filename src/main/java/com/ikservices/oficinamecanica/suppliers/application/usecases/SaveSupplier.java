package com.ikservices.oficinamecanica.suppliers.application.usecases;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

public class SaveSupplier {
	private final SupplierRepository repository;
	
	public SaveSupplier(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public Supplier execute(Supplier supplier) {
		return repository.saveSupplier(supplier);
	}
}
