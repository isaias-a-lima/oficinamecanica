package com.ikservices.oficinamecanica.suppliers.application.usecases;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;

public class GetNextSupplierId {
	private final SupplierRepository repository;
	
	public GetNextSupplierId(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public Long execute(Long workshopId) {
		return repository.getNextSupplierId(workshopId);
	}
}
