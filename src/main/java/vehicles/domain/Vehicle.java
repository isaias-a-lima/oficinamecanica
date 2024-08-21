package vehicles.domain;

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
	Workshop workshop;
	String plate;
	String brand;
	String model;
	String manufacturing;
	String engine;
	String observations;
	boolean active;
}
