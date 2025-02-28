package com.ikservices.oficinamecanica.categories.domain;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Category {
	private CategoryId id;
	private LocalDate creationDate;
	private String description;
	private CategoryTypeEnum categType;
	private Boolean activated;
}
