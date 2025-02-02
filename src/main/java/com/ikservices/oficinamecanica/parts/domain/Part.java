package com.ikservices.oficinamecanica.parts.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Part {
	private PartId partId;
	private Workshop workshop;
	private String description;
	private String brand;
	private String fits;
	private String manufacturerCode;
	private BigDecimal cost;
	private BigDecimal profit;
	private Integer balance;

	public Part(PartId partId) {
		this.partId = partId;
	}

	public BigDecimal getValue() {
		return cost.add(cost.multiply(profit.divide(BigDecimal.valueOf(100)))).setScale(2, RoundingMode.HALF_UP);
	}
}
