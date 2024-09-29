package com.ikservices.oficinamecanica.suppliers.domain;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.vo.AddressVO;
import com.ikservices.oficinamecanica.commons.vo.EmailVO;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
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
	private IdentificationDocumentVO idDoc;
	private Workshop workshop;
	private String name;
	private PhoneVO landline;
	private PhoneVO mobilePhone;
	private EmailVO email;
	private AddressVO address;
	private TaxPayerEnum type;
}
