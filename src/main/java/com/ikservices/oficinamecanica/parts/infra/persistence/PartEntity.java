package com.ikservices.oficinamecanica.parts.infra.persistence;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.ikservices.oficinamecanica.suppliers.infra.persistence.SupplierEntity;
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
	@ManyToMany(mappedBy = "parts", cascade = CascadeType.ALL)
	private Set<SupplierEntity> suppliers;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "COST")
	private BigDecimal cost;
	@Column(name = "BALANCE")
	private Integer balance;
	@Column(name = "PROFIT")
	private BigDecimal profit;
	@Column(name = "BRAND")
	private String brand;
	
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
		if(Objects.nonNull(entity.getBrand())) {
			this.brand = entity.getBrand();
		}
	}
}
