package com.ikservices.oficinamecanica.services.domain;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.workshops.domain.Workshop;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Service {
	private Workshop workshop;
	private String description;
	private BigDecimal value;
}
