package com.ikservices.oficinamecanica.workorders.infra.persistence;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.items.parts.infra.persistence.WorkOrderPartItemEntity;
import com.ikservices.oficinamecanica.workorders.items.services.infra.persistence.WorkOrderServiceItemEntity;
import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
	
	@OneToOne(fetch = FetchType.EAGER)
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

	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	@Column(name = "PAYQTY")
	private Integer payQty;
	
	@Column(name = "PAID")
	private Boolean paid;

	@OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkOrderServiceItemEntity> serviceItems;
	
	@OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WorkOrderPartItemEntity> partItems;

	@OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PaymentEntity> payments;

	public void update(WorkOrderEntity entity) {

		if(Objects.nonNull(entity.getKm())) {
			this.km = entity.getKm();
		}

		if(Objects.nonNull(entity.getWoStatus())) {
			this.woStatus = entity.getWoStatus();
		}

		if(Objects.nonNull(entity.getPayQty())) {
			this.payQty = entity.getPayQty();
		}

		this.discount = entity.getDiscount();

		this.updateServiceItems(entity.getServiceItems());
		
		this.updatePartItems(entity.getPartItems());
	}

	private void updateAmount() {
		BigDecimal sum = BigDecimal.ZERO;

		for (WorkOrderServiceItemEntity serviceItem : serviceItems) {
			sum = sum.add(serviceItem.getTotal());
		}
		
		for (WorkOrderPartItemEntity partItem : partItems) {
			sum = sum.add(partItem.getTotal());
		}
		this.amount = sum;
	}

	//Service items begin
	
	private void updateServiceItems(List<WorkOrderServiceItemEntity> newItems) {
		
		this.serviceItems.removeIf(existingItem ->
			newItems.stream().noneMatch(newItem -> newItem.getId().equals(existingItem.getId()))
		);
		
		for(WorkOrderServiceItemEntity newItem : newItems) {
			
			Optional<WorkOrderServiceItemEntity> existingItemOptional = this.serviceItems.stream()
					.filter(existingItem -> existingItem.getId().equals(newItem.getId()))
					.findFirst();
			
			if (existingItemOptional.isPresent()) {
				WorkOrderServiceItemEntity existingItem = existingItemOptional.get();
				existingItem.update(newItem);
			} else {
				this.serviceItemIncluding(newItem);
			}
		}
		
		this.updateAmount();
	}
	
	public void addServiceItem(WorkOrderServiceItemEntity serviceItem) {
		this.serviceItemIncluding(serviceItem);
		this.updateAmount();
	}
	
	private void serviceItemIncluding(WorkOrderServiceItemEntity serviceItem) {
		serviceItem.getId().setItemId(getNextServiceId());
		this.serviceItems.add(serviceItem);
	
	}
	
	private Long getNextServiceId() {
		return this.serviceItems.isEmpty() ? 1 : this.serviceItems.get(this.serviceItems.size() - 1).getId().getItemId() + 1;
	}
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

	//Payments begin
	public void updatePayments(List<PaymentEntity> newEntityList) {

		for (PaymentEntity oldPayment : this.payments) {

			int index = newEntityList.indexOf(oldPayment);
			if (index >= 0) {
				oldPayment.update(newEntityList.get(index));
			}
		}

		for (PaymentEntity newPayment : newEntityList) {
			boolean isToAdd = !this.payments.contains(newPayment);

			if (isToAdd) {
				this.payments.add(newPayment);
			}

		}


		/*
		this.payments.removeIf(existingItem ->
				newEntityList.stream().noneMatch(newEntity -> newEntity.equals(existingItem))
		);

		for (PaymentEntity newEntity : newEntityList) {

			Optional<PaymentEntity> existingItemOptional = this.payments.stream()
					.filter(existingItem -> existingItem.equals(newEntity))
					.findFirst();

			if (existingItemOptional.isPresent()) {
				PaymentEntity existingItem = existingItemOptional.get();
				existingItem.update(newEntity);
			} else {
				this.setNextPaymentNumberId(newEntity);
			}
		}

		 */

	}

	private void setNextPaymentNumberId(PaymentEntity installment) {
		Integer nextId = this.payments.isEmpty() ? 1 : this.payments.get(this.payments.size() - 1).getId().getNumber() + 1;
		installment.getId().setNumber(nextId);
		this.payments.add(installment);
	}

	public void addPayments(List<PaymentEntity> paymentEntityList) {
		this.payments.addAll(paymentEntityList);
	}
	//Payments end
}
