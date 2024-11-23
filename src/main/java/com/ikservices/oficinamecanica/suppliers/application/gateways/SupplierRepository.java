package com.ikservices.oficinamecanica.suppliers.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;

public interface SupplierRepository {
	Supplier saveSupplier(Supplier supplier);
	Supplier updateSupplier(Supplier supplier);
	Supplier getSupplier(SupplierId supplierId);
	List<Supplier> getSupplierList(Long workshopId, String search);
	Long getNextSupplierId(Long workshopId);
}
