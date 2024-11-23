package com.ikservices.oficinamecanica.vehicles.domain;

import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

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
public class Vehicle {
	Customer customer;
	String plate;
	String brand;
	String model;
	String color;
	FuelEnum fuel;
	TransmissionEnum transmission;
	String manufacturing;
	String engine;
	String observations;
	boolean active;
}
