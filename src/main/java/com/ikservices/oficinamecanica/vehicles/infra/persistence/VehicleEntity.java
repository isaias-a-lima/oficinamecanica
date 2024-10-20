package com.ikservices.oficinamecanica.vehicles.infra.persistence;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;

import com.ikservices.oficinamecanica.vehicles.domain.FuelEnum;
import com.ikservices.oficinamecanica.vehicles.domain.TransmissionEnum;
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
@Entity
@Table(name = "VEHICLES")
public class VehicleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLEID")
	private Long vehicleId;

	@Column(name = "IDDOC")
	private String idDoc;

	@Column(name = "WORKSHOPID")
	private Long workshopId;
	
	@ManyToOne
	@JoinColumn(name = "IDDOC", referencedColumnName = "DOCID", updatable = false, insertable = false)
	@JoinColumn(name = "WORKSHOPID", referencedColumnName = "WORKSHOPID", updatable = false, insertable = false)
	private CustomerEntity customerEntity;
	
	@Column(name = "PLATE")
	private String plate;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "MODEL")
	private String model;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "FUEL")
	@Enumerated(EnumType.ORDINAL)
	private FuelEnum fuel;

	@Column(name = "TRANSMISSION")
	@Enumerated(EnumType.ORDINAL)
	private TransmissionEnum transmission;
	
	@Column(name = "MANUFACTURING")
	private String manufacturing;
	
	@Column(name = "ENGINE")
	private String engine;
	
	@Column(name = "OBSERVATIONS")
	private String observations;
	
	@Column(name = "ACTIVE")
	private Boolean active;

	@OneToMany(mappedBy = "vehicleEntity")
	private List<BudgetEntity> budgetList;
	
	public void update(VehicleEntity entity) {
		if(Objects.nonNull(entity.getPlate())) {
			this.plate = entity.getPlate();
		}
		if(Objects.nonNull(entity.getBrand())) {
			this.brand = entity.getBrand();
		}
		if(Objects.nonNull(entity.getModel())) {
			this.model = entity.getModel();
		}
		if(Objects.nonNull(entity.getColor())) {
			this.color = entity.getColor();
		}
		if(Objects.nonNull(entity.getFuel())) {
			this.fuel = entity.getFuel();
		}
		if(Objects.nonNull(entity.getTransmission())) {
			this.transmission = entity.getTransmission();
		}
		if(Objects.nonNull(entity.getManufacturing())) {
			this.manufacturing = entity.getManufacturing();
		}
		if(Objects.nonNull(entity.getEngine())) {
			this.engine = entity.getEngine();
		}
		if(Objects.nonNull(entity.getObservations())) {
			this.observations = entity.getObservations();
		}
	}
	
	public void delete() {
		this.active = false;
	}
	
	public void restore() {
		this.active = true;
	}
}
