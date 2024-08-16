package com.ikservices.oficinamecanica.suppliersparts.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.suppliersparts.application.gateway.SupplierPartRepository;
import com.ikservices.oficinamecanica.suppliersparts.domain.SupplierPart;

public class ListSupplierPart {
	private final SupplierPartRepository repository;
	
	public ListSupplierPart(SupplierPartRepository repository) {
		this.repository = repository;
	}
	
	public List<SupplierPart> execute(Long workshopId) {
		return repository.listSupplierPart(workshopId);
	}
}
