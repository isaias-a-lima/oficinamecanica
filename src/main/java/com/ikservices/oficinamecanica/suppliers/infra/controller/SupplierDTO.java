package com.ikservices.oficinamecanica.suppliers.infra.controller;

import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SupplierDTO {
	private Long supplierId;
	private Long workshopId;
	private String name;
	private String landLine;
	private String mobilePhone;
	private String email;
	private String postalCode;
	private String address;
	private String city;
	private String state;
	
	public SupplierDTO(Supplier supplier) {
		this.supplierId = supplier.getSupplierId().getId();
		this.workshopId = supplier.getSupplierId().getWorkshopid();
		this.name = supplier.getName();
		this.landLine = supplier.getLandline();
		this.mobilePhone = supplier.getMobilePhone();
		this.email = supplier.getEmail();
		this.postalCode = supplier.getPostalCode();
		this.address = supplier.getAddress();
		this.city = supplier.getCity();
		this.state = supplier.getState();
	}
}
