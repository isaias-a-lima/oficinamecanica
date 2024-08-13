package com.ikservices.oficinamecanica.suppliersparts.domain;

import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class SupplierPart {
	Workshop workshop;
	Supplier supplier;
	Part part;
}
