package com.ikservices.oficinamecanica.payables.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Payable {
	private PayableId id;
	private LocalDate creationDate;
	private String description;
	private String docNumber;
	private LocalDate dueDate;
	private BigDecimal payValue;
	private LocalDate payDate;
	private Category category;
}
