package com.ikservices.oficinamecanica.parts.infra.controller;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.parts.domain.Part;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class PartDTO {
	private Long partId;
	private Long workshopId;
	private int balance;
	private BigDecimal profit;
	private String description;
	private BigDecimal cost;
	
	public PartDTO(Part part) {
		this.partId = part.getPartId().getId();
		this.workshopId = part.getPartId().getWorkshopId();
		this.balance = part.getBalance();
		this.profit = part.getProfit();
		this.cost = part.getCost();
		this.description = part.getDescription();
	}
}
