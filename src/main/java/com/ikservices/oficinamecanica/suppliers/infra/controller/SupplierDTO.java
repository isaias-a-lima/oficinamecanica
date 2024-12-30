package com.ikservices.oficinamecanica.suppliers.infra.controller;

import com.ikservices.oficinamecanica.parts.infra.controller.PartDTO;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SupplierDTO {
	private Long supplierId;
	private Long workshopId;
	private Set<PartDTO> parts;
	private String idDoc;
	private String name;
	private String landLine;
	private String mobilePhone;
	private String email;
	private String postalCode;
	private String address;
	private String city;
	private String state;
	private String type;
}
