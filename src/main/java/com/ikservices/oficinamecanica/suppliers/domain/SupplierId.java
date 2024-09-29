package com.ikservices.oficinamecanica.suppliers.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SupplierId implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long workshopid;
}
