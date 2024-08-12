package com.ikservices.oficinamecanica.suppliers.infra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
	private GetSupplierList getSupplierList;
	private SupplierConverter converter;
	
	public SupplierController(GetSupplierList getSupplierList, 
			SupplierConverter converter) {
		this.getSupplierList = getSupplierList;
		this.converter = converter;
	}
	
	@GetMapping("workshop/{workshopId}")
	public ResponseEntity<IKResponse<SupplierDTO>> getSupplierList(@PathVariable("workshopId") Long workshopId) {
		List<SupplierDTO> supplierList = converter.parseDTOList(getSupplierList.execute(workshopId));
		
		return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(supplierList));
	}
}
