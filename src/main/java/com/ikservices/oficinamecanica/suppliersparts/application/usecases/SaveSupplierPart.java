package com.ikservices.oficinamecanica.suppliersparts.application.usecases;

import com.ikservices.oficinamecanica.suppliersparts.application.gateway.SupplierPartRepository;
import com.ikservices.oficinamecanica.suppliersparts.domain.SupplierPart;

public class SaveSupplierPart {
	private final SupplierPartRepository repository;
	
	public SaveSupplierPart(SupplierPartRepository repository) {
		this.repository = repository;
	}
	
	public SupplierPart execute(SupplierPart supplierPart) {
		return repository.saveSupplierPart(supplierPart);
	}
}
