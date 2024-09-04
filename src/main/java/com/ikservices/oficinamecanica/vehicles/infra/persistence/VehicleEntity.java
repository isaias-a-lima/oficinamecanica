package com.ikservices.oficinamecanica.vehicles.infra.persistence;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;

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
	
	@ManyToOne
	@JoinColumn(name = "IDDOC", referencedColumnName = "DOCID", insertable = false, updatable = false)
	@JoinColumn(name = "WORKSHOPID", referencedColumnName = "WORKSHOPID", insertable = false, updatable = false)
	private CustomerEntity customerEntity;
	
	@Column(name = "PLATE")
	private String plate;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "MANUFACTURING")
	private String manufacturing;
	
	@Column(name = "ENGINE")
	private String engine;
	
	@Column(name = "OBSERVATIONS")
	private String observations;
	
	@Column(name = "ACTIVE")
	private Boolean active;
	
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
