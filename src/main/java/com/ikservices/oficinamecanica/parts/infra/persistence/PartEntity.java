package com.ikservices.oficinamecanica.parts.infra.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "PARTS")
public class PartEntity {
	@EmbeddedId
	private PartEntityId id;
	@ManyToOne
	@JoinColumn(name = "WORKSHOPID", insertable = false, updatable = false)
	private WorkshopEntity workshopEntity;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "COST")
	private BigDecimal cost;
	@Column(name = "BALANCE")
	private int balance;
	@Column(name = "PROFIT")
	private BigDecimal profit;
	
	public void update(PartEntity entity) {
		if(Objects.nonNull(entity.getDescription())) {
			this.description = entity.getDescription();
		}
		if(Objects.nonNull(entity.getCost())) {
			this.cost = entity.getCost();
		}
		if(Objects.nonNull(entity.getBalance())){
			this.balance = entity.getBalance();
		}
		if(Objects.nonNull(entity.getProfit())){
			this.profit = entity.getProfit();
		}
	}
}
