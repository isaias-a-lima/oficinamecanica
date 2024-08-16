package com.ikservices.oficinamecanica.suppliersparts.application.gateway;

import java.util.List;

import com.ikservices.oficinamecanica.suppliersparts.domain.SupplierPart;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

public interface SupplierPartRepository {
	SupplierPart saveSupplierPart(SupplierPart supplierPart);
	SupplierPart updateSupplierPart(SupplierPart supplierPart);
	SupplierPart getSupplierPart(Long workshopId, Integer supplierId, Integer partId);
	List<SupplierPart> listSupplierPart(Long workshopId);
}
