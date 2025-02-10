package com.ikservices.oficinamecanica.workorders.infra.persistence;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence.BudgetItemPartEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.infra.persistence.WorkOrderInstallmentEntity;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
	private WorkOrderStatusEnum woStatus;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;	
	
	@Column(name = "PAYFORM")
	@Enumerated(EnumType.ORDINAL)
	private PayFormEnum payForm;
	
	@Column(name = "PAYQTY")
	private Integer payQty;
	
	@Column(name = "PAID")
	private Boolean paid;
	
	@OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkOrderInstallmentEntity> installments;

	//TODO Here will be the service items list

	@OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkOrderPartItemEntity> partItems;
	
	public void update(WorkOrderEntity entity) {
		if(Objects.nonNull(entity.getKm())) {
			this.km = entity.getKm();
		}
		if(Objects.nonNull(entity.getWoStatus())) {
			this.woStatus = entity.getWoStatus();
		}
		if(Objects.nonNull(entity.getPayForm())) {
			this.payForm = entity.getPayForm();
		}
		if(Objects.nonNull(entity.getPayQty())) {
			this.payQty = entity.getPayQty();
		}

		//TODO There will be the invocation of service items update here.

		this.updatePartItems(entity.getPartItems());
	}

	private void updateAmount() {
		BigDecimal sum = BigDecimal.ZERO;

		//TODO There will be a FOR of service items here.

		for (WorkOrderPartItemEntity partItem : partItems) {
			sum = sum.add(partItem.getTotal());
		}
		this.amount = sum;
	}

	//Service items begin
	//TODO Here will be the service items update business rules.
	//Service items end

	//Part items begin
	private void updatePartItems(List<WorkOrderPartItemEntity> newItems) {

		this.partItems.removeIf(existingItem ->
				newItems.stream().noneMatch(newItem -> newItem.getId().equals(existingItem.getId()))
		);

		for (WorkOrderPartItemEntity newItem : newItems) {

			Optional<WorkOrderPartItemEntity> existingItemOptional = this.partItems.stream()
					.filter(existingItem -> existingItem.getId().equals(newItem.getId()))
					.findFirst();

			if (existingItemOptional.isPresent()) {
				WorkOrderPartItemEntity existingItem = existingItemOptional.get();
				existingItem.update(newItem);
			} else {
				this.partItemIncluding(newItem);
			}
		}

		this.updateAmount();
	}

	public void addPartItem(WorkOrderPartItemEntity partItem) {
		this.partItemIncluding(partItem);
		this.updateAmount();
	}

	private void partItemIncluding(WorkOrderPartItemEntity partItem) {
		partItem.getId().setItemId(getNextPartId());
		this.partItems.add(partItem);
	}

	private Long getNextPartId() {
		return this.partItems.isEmpty() ? 1 : this.partItems.get(this.partItems.size() - 1).getId().getItemId() + 1;
	}
	//Part items end
}
