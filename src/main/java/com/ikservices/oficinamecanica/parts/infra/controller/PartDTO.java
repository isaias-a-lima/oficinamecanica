package com.ikservices.oficinamecanica.parts.infra.controller;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
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
	private Integer balance;
	private String profit;
	private String description;
	private String cost;
	private String brand;
	private String value;
	
	public PartDTO(Part part) {
		this.partId = part.getPartId().getId();
		this.workshopId = part.getPartId().getWorkshopId();
		this.balance = part.getBalance();
		this.profit = NumberUtil.parseStringPercent(part.getProfit());
		this.cost = NumberUtil.parseStringMoney(part.getCost());
		this.description = part.getDescription();
		this.brand = part.getBrand();
		this.value = NumberUtil.parseStringMoney(part.getValue());
	}
}
