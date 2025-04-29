package com.ikservices.oficinamecanica.payables.infra.controller;

import com.ikservices.oficinamecanica.categories.infra.controller.CategoryDTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PayableDTO {
	private Integer id;
	private Long workshopId;
	private String creationDate;
	private String description;
	private String docNumber;
	private String dueDate;
	private String payValue;
	private String payDate;
	private CategoryDTO categoryDTO;
}
