package com.ikservices.oficinamecanica.suppliers.infra.persistence;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "SUPPLIERS")
public class SupplierEntity {
	@EmbeddedId
	private SupplierEntityId id;
	@ManyToOne
	@JoinColumn(name = "WORKSHOPID", insertable = false, updatable = false)
	private WorkshopEntity workshopEntity;
	@Column(name = "IDDOC")
	private String idDoc;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LANDLINE")
	private String landLine;
	@Column(name = "MOBILEPHONE")
	private String mobilePhone;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "POSTALCODE")
	private String postalCode;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "CITY")
	private String city;
	@Column(name = "STATE")
	private String state;
	@Column(name = "TYPE")
	private Character type;
	
	public void update(SupplierEntity entity) {
		if(Objects.nonNull(entity.getIdDoc())) {
			this.idDoc = entity.getIdDoc();
		}
		if(Objects.nonNull(entity.getName())) {
			this.name = entity.getName();
		}
		if(Objects.nonNull(entity.getLandLine())) {
			this.landLine = entity.getLandLine();
		}
		if(Objects.nonNull(entity.getMobilePhone())) {
			this.mobilePhone = entity.getMobilePhone();
		}
		if(Objects.nonNull(entity.getEmail())) {
			this.email = entity.getEmail();
		}
		if(Objects.nonNull(entity.getPostalCode())) {
			this.postalCode = entity.getPostalCode();
		}
		if(Objects.nonNull(entity.getAddress())) {
			this.address = entity.getAddress();
		}
		if(Objects.nonNull(entity.getCity())) {
			this.city = entity.getCity();
		}
		if(Objects.nonNull(entity.getState())) {
			this.state = entity.getState();
		}
		if(Objects.nonNull(entity.getType())) {
			this.type = entity.getType();
		}
	}
}
