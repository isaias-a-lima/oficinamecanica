package com.ikservices.oficinamecanica.categories.infra.controller;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.categories.domain.CategoryTypeEnum;

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
	private LocalDate creationDate;
	private String description;
	private CategoryTypeEnum categType;
	private boolean activated;
}
