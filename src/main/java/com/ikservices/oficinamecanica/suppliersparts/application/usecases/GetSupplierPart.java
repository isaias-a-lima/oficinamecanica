package com.ikservices.oficinamecanica.suppliersparts.application.usecases;

import com.ikservices.oficinamecanica.suppliersparts.application.gateway.SupplierPartRepository;
import com.ikservices.oficinamecanica.suppliersparts.domain.SupplierPart;

public class GetSupplierPart {
	private final SupplierPartRepository repository;
	
	public GetSupplierPart(SupplierPartRepository repository) {
		this.repository = repository;
	}
	
	public SupplierPart execute(Long workshopId, Integer supplierId, Integer partId) {
		return repository.getSupplierPart(workshopId, supplierId, partId);
	}
}
