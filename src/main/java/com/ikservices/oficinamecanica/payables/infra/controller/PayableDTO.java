package com.ikservices.oficinamecanica.payables.infra.controller;

import com.ikservices.oficinamecanica.categories.infra.controller.CategoryDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "workshopId"})
public class PayableDTO implements Serializable {
	private Integer id;
	private Long workshopId;
	private String creationDate;
	private String description;
	private String docNumber;
	private String dueDate;
	private String payValue;
	private String payDate;
	private CategoryDTO category;
	private String note;
	private Integer supplierId;
}
