package com.ikservices.oficinamecanica.workorders.installments.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.infra.persistence.WorkOrderEntity;
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

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "WORKORDERID", referencedColumnName = "WORKORDERID", updatable = false, insertable = false),
			@JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
	})
	private WorkOrderEntity workOrder;
	
	@Column(name = "DUEDATE")
	private LocalDate dueDate;
	
	@Column(name = "PAYVALUE")
	private BigDecimal payValue;

	@Column(name = "PAYFORM")
	@Enumerated(EnumType.ORDINAL)
	private PayFormEnum payForm;
	
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
