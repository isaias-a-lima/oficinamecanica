package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.infra.persistence.WorkOrderInstallmentEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "WORK_ORDERS")
public class WorkOrderEntity {
	@EmbeddedId
	private WorkOrderEntityId id;
	
	@OneToOne
	@JoinColumn(name = "BUDGETID", insertable = false, updatable = false)
	private BudgetEntity budget;
	
	@Column(name = "OPENINGDATE")
	private LocalDate openingDate;
	
	@Column(name = "KM")
	private Long km;
	
	@Column(name = "WOSTATUS")
	@Enumerated(EnumType.ORDINAL)
	private WorkOrderStatusEnum wostatus;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;	
	
	@Column(name = "PAYFORM")
	@Enumerated(EnumType.ORDINAL)
	private PayFormEnum payForm;
	
	@Column(name = "PAYQTY")
	private Integer payQty;
	
	@Column(name = "PAID")
	private Boolean paid;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name = "WORKORDERID", referencedColumnName = "WORKORDERID"),
		@JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID")
	})
	private List<WorkOrderInstallmentEntity> installments;
	
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
		if(Objects.nonNull(entity.getPayQty())) {
			this.payQty = entity.getPayQty();
		}
	}
}
