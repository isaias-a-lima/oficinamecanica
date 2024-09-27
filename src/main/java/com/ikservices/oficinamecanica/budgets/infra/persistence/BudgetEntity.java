package com.ikservices.oficinamecanica.budgets.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;

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
@Table(name = "BUDGETS")
public class BudgetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BUDGETID")
	private Long budgetId;
	
	@ManyToOne
	@Column(name = "VEHICLEID")
	private VehicleEntity vehicleEntity;
	
	@Column(name = "OPENINGDATE")
	private LocalDate openingDate;
	
	@Column(name = "KM")
	private Long km;
	
	@Column(name = "BSTATUS")
	private Character budgetStatus;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	public void update(BudgetEntity entity) {
		if(Objects.nonNull(entity.getOpeningDate())) {
			this.openingDate = entity.getOpeningDate(); 
		}
		if(Objects.nonNull(entity.getKm())) {
			this.km = entity.getKm();
		}
		if(Objects.nonNull(entity.getBudgetStatus())) {
			this.budgetStatus = entity.getBudgetStatus();
		}
		if(Objects.nonNull(entity.getAmount())) {
			this.amount = entity.getAmount();
		}
	}
}
