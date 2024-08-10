package com.ikservices.oficinamecanica.suppliers.application.usecases;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

public class GetSupplier {
	private final SupplierRepository repository;
	
	public GetSupplier(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public Supplier execute(Long workshopId) {
		return repository.getSupplier(workshopId);
	}
}
