package com.ikservices.oficinamecanica.services.domain;

import java.math.BigDecimal;

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
public class Service {
	private ServiceId id;
	private Workshop workshop;
	private String description;
	private BigDecimal cost;
}
