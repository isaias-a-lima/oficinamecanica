package com.ikservices.oficinamecanica.workorders.installments.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "WO_INSTALLMENTS")
public class WorkOrderInstallmentEntity {
	@EmbeddedId
	private WorkOrderInstallmentEntityId id;
	
	@Column(name = "DUEDATE")
	private LocalDate dueDate;
	
	@Column(name = "PAYVALUE")
	private BigDecimal payValue;
	
	@Column(name = "PAYDATE")
	private LocalDate payDate;
	
	@Column(name = "NOTE")
	private String note;
	
	public void update(WorkOrderInstallmentEntity entity) {
		if(Objects.nonNull(entity.getDueDate())) {
			this.dueDate = entity.getDueDate();
		}
		if(Objects.nonNull(entity.getPayValue())) {
			this.payValue = entity.getPayValue();
		}
		if(Objects.nonNull(entity.getPayDate())) {
			this.payDate = entity.getPayDate();
		}
		if(Objects.nonNull(entity.getNote())) {
			this.note = entity.getNote();
		}
	}
}
