package com.ikservices.oficinamecanica.services.infra.controller;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.services.domain.Service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ServiceDTO {
	Long serviceId;
	Long workshopId;
	String description;
	BigDecimal cost;
	
	public ServiceDTO(Service service) {
		this.serviceId = service.getId().getId();
		this.workshopId = service.getId().getWorkshopId();
		this.description = service.getDescription();
		this.cost = service.getCost();
	}
}
