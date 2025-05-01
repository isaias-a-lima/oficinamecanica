package com.ikservices.oficinamecanica.categories.infra.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDTO {
	private Integer categoryId;
	private Long workshopId;
	private String creationDate;
	private String description;
	private Integer categoryType;
	private boolean activated;
}
