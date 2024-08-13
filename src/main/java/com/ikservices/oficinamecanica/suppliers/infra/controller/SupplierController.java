package com.ikservices.oficinamecanica.suppliers.infra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
	private GetSupplierList getSupplierList;
	private SupplierConverter converter;
	private GetSupplier getSupplier;
	
	public SupplierController(GetSupplierList getSupplierList, 
			SupplierConverter converter, GetSupplier getSupplier) {
		this.getSupplierList = getSupplierList;
		this.converter = converter;
		this.getSupplier = getSupplier;
	}
	
	@GetMapping("workshop/{workshopId}")
	public ResponseEntity<IKResponse<SupplierDTO>> getSupplierList(@PathVariable("workshopId") Long workshopId) {
		List<SupplierDTO> supplierList = converter.parseDTOList(getSupplierList.execute(workshopId));
		
		return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(supplierList));
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<SupplierDTO>> getSupplier(@PathVariable Long workshopId,
			@PathVariable Long id) {
		Supplier supplier = getSupplier.execute(new SupplierId(id, workshopId));
		return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier)));
	}
}
