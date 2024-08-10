package com.ikservices.oficinamecanica.suppliers.domain;

import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
	private SupplierId supplierId;
	private Workshop workshop;
	private String name;
	private String landline;
	private String mobilePhone;
	private String email;
	private String postalCode;
	private String address;
	private String city;
	private String state;
}
