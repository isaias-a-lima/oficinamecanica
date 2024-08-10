package com.ikservices.oficinamecanica.suppliers.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

public interface SupplierRepository {
	Supplier saveSupplier(Supplier supplier);
	Supplier updateSupplier(Supplier supplier);
	Supplier getSupplier(Long workshopId);
	List<Supplier> getSupplierList(Long workshopId);
	Long getNextSupplierId(Long workshopId);
}
