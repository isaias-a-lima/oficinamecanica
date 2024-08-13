package com.ikservices.oficinamecanica.suppliers.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.parts.infra.controller.PartDTO;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetNextSupplierId;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.application.usecases.SaveSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.UpdateSupplier;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;

@RestController
@RequestMapping("suppliers")
public class SupplierController {
	private GetSupplierList getSupplierList;
	private SupplierConverter converter;
	private GetSupplier getSupplier;
	private UpdateSupplier updateSupplier;
	private SaveSupplier saveSupplier;
	private GetNextSupplierId getNextSupplierId;
	
	public SupplierController(GetSupplierList getSupplierList, 
			SupplierConverter converter, GetSupplier getSupplier, UpdateSupplier updateSupplier,
			SaveSupplier saveSupplier, GetNextSupplierId getNextSupplierId) {
		this.getSupplierList = getSupplierList;
		this.converter = converter;
		this.getSupplier = getSupplier;
		this.saveSupplier = saveSupplier;
		this.updateSupplier = updateSupplier;
		this.getNextSupplierId = getNextSupplierId;
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
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<SupplierDTO>> updateSupplier(@RequestBody SupplierDTO dto) {
		Supplier supplier = updateSupplier.execute(converter.parseSupplier(dto));
		return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier)));
	}
	
	@PostMapping
	public ResponseEntity<IKResponse<SupplierDTO>> saveSupplier(@RequestBody SupplierDTO dto, UriComponentsBuilder uriBuilder) {
		Supplier supplier = null;
		
		try {
			Long nextId = getNextSupplierId.execute(dto.getWorkshopId());
			dto.setSupplierId(nextId);
			supplier = saveSupplier.execute(converter.parseSupplier(dto));
			URI uri = uriBuilder.path("suppliers/{workshopId}/{id}").
					buildAndExpand(supplier.getSupplierId().getWorkshopid(),
							supplier.getSupplierId().getId()).toUri();
			return ResponseEntity.created(uri).body(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier)));
		}catch(IKException ike) {
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			return ResponseEntity.status(code).body(IKResponse.<SupplierDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
		}
	}
}
