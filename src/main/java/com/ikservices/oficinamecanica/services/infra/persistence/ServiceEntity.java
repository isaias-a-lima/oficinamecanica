package com.ikservices.oficinamecanica.services.infra.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.workshops.domain.Workshop;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SERVICES")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceEntity {
	@EmbeddedId
	private ServiceEntityId id;
	@ManyToOne
	@JoinColumn(name = "WORKSHOPID", insertable = false, updatable = false)
	private WorkshopEntity workshop;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "COST")
	private BigDecimal cost;
	
	public void update(ServiceEntity entity) {
		if(Objects.nonNull(entity.getDescription())) {
			this.description = entity.getDescription();
		}
		if(Objects.nonNull(entity.getCost())) {
			this.cost = entity.getCost();
		}
	}
}
