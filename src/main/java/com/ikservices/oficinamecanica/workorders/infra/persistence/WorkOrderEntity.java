package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.workorders.domain.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderStatusEnum;
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
@Table(name = "WORK_ORDERS")
public class WorkOrderEntity {
	@EmbeddedId
	private WorkOrderEntityId workOrderEntityId;
	
	@JoinColumn(name = "VEHICLEID", insertable = false, updatable = false)
	private Long vehicleId;
	
	@JoinColumn(name = "BUDGETID", insertable = false, updatable = false)
	private Long budgetId;
	
	@Column(name = "OPENINGDATE")
	private LocalDate openingDate;
	
	@Column(name = "KM")
	private Long km;
	
	@Column(name = "WOSTATUS")
	@Enumerated(EnumType.ORDINAL)
	private WorkOrderStatusEnum wostatus;
	
	@Column(name = "PAID")
	private Boolean paid;
	
	@Column(name = "PAYFORM")
	@Enumerated(EnumType.ORDINAL)
	private PayFormEnum payForm;
	
	@Column(name = "INSTALLMENTS")
	private Integer installments;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	public void update(WorkOrderEntity entity) {
		if(Objects.nonNull(entity.getKm())) {
			this.km = entity.getKm();
		}
		if(Objects.nonNull(entity.getWostatus())) {
			this.wostatus = entity.getWostatus();
		}
		if(Objects.nonNull(entity.getPaid())) {
			this.paid = entity.getPaid();
		}
		if(Objects.nonNull(entity.getPayForm())) {
			this.payForm = entity.getPayForm();
		}
		if(Objects.nonNull(entity.getInstallments())) {
			this.installments = entity.getInstallments();
		}
		if(Objects.nonNull(entity.getAmount())) {
			this.amount = entity.getAmount();
		}
	}
}
