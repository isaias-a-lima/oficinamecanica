package com.ikservices.oficinamecanica.services.infra.controller;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
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
	private Long serviceId;
	private Long workshopId;
	private String description;
	private String cost;
	
	public ServiceDTO(Service service) {
		this.serviceId = service.getId().getId();
		this.workshopId = service.getId().getWorkshopId();
		this.description = service.getDescription();
		this.cost = NumberUtil.parseStringMoney(service.getCost());
	}
}
