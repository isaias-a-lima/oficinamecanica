package com.ikservices.oficinamecanica.vehicles.infra.controller;

import com.ikservices.oficinamecanica.customers.infra.controller.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VehicleDTO {
	CustomerDTO customerDTO;
	String plate;
	String brand;
	String model;
	String manufacturing;
	String engine;
	String observations;
}
