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
	private String description;
	private String brand;
	private String fits;
	private String manufacturerCode;
	private String cost;
	private String profit;
	private String value;
	private Integer balance;
	
	public PartDTO(Part part) {
		this.partId = part.getPartId().getId();
		this.workshopId = part.getPartId().getWorkshopId();
		this.description = part.getDescription();
		this.brand = part.getBrand();
		this.fits = part.getFits();
		this.manufacturerCode = part.getManufacturerCode();
		this.cost = NumberUtil.parseStringMoney(part.getCost());
		this.profit = NumberUtil.parseStringPercent(part.getProfit());
		this.value = NumberUtil.parseStringMoney(part.getValue());
		this.balance = part.getBalance();
	}
}
