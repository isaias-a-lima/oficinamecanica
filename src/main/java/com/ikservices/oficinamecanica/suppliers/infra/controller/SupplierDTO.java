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
	private String idDoc;
	private String name;
	private String landLine;
	private String mobilePhone;
	private String email;
	private String postalCode;
	private String address;
	private String city;
	private String state;
	private Character type;
	
	public SupplierDTO(Supplier supplier) {
		this.supplierId = supplier.getSupplierId().getId();
		this.workshopId = supplier.getSupplierId().getWorkshopid();
		this.idDoc = supplier.getIdDoc().getFullDocument();
		this.name = supplier.getName();
		this.landLine = supplier.getLandline().getFullPhone();
		this.mobilePhone = supplier.getMobilePhone().getFullPhone();
		this.email = supplier.getEmail().getMailAddress();
		this.postalCode = supplier.getAddress().getFormattedPostalCode();
		this.address = supplier.getAddress().getStreet();
		this.city = supplier.getAddress().getCity();
		this.state = supplier.getAddress().getState();
		this.type = supplier.getType().getType();
	}
}
