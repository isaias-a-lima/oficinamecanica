package com.ikservices.oficinamecanica.suppliersparts.application.usecases;

import com.ikservices.oficinamecanica.suppliersparts.application.gateway.SupplierPartRepository;
import com.ikservices.oficinamecanica.suppliersparts.domain.SupplierPart;

public class UpdateSupplierPart {
	private final SupplierPartRepository repository;
	
	public UpdateSupplierPart(SupplierPartRepository repository) {
		this.repository = repository;
	}
	
	public SupplierPart execute(SupplierPart supplierPart) {
		return repository.updateSupplierPart(supplierPart);
	}
}
