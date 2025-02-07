package com.ikservices.oficinamecanica.workorders.items.services.domain;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.services.domain.Service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkOrderServiceItem {
	WorkOrderServiceItemId itemId;
	Service service;
	Integer quantity;
	BigDecimal itemValue;
	BigDecimal discount;
}

