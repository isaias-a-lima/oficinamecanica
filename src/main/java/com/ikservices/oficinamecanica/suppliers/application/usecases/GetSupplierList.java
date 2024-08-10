package com.ikservices.oficinamecanica.suppliers.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.suppliers.application.gateways.SupplierRepository;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

public class GetSupplierList {
	private final SupplierRepository repository;
	
	public GetSupplierList(SupplierRepository repository) {
		this.repository = repository;
	}
	
	public List<Supplier> execute(Long workshopId) {
		return repository.getSupplierList(workshopId);
	}
}
