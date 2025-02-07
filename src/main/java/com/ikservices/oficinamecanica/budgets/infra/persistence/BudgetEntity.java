package com.ikservices.oficinamecanica.budgets.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.*;

import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence.BudgetItemPartEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
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

	@Column(name = "VEHICLEID")
	private Long vehicleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "VEHICLEID", referencedColumnName = "VEHICLEID", updatable = false, insertable = false)
	private VehicleEntity vehicle;
	
	@Column(name = "OPENINGDATE")
	private LocalDate openingDate;
	
	@Column(name = "KM")
	private Long km;
	
	@Column(name = "BSTATUS")
	@Enumerated(EnumType.ORDINAL)
	private BudgetStatusEnum budgetStatus;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@OneToMany(mappedBy = "budgetEntity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetItemServiceEntity> serviceItems = new ArrayList<>();

	@OneToMany(mappedBy = "budgetEntity",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetItemPartEntity> partItems = new ArrayList<>();
	
	public void update(BudgetEntity entity) {
		if(Objects.nonNull(entity.getKm())) {
			this.km = entity.getKm();
		}

		this.updateServiceItems(entity.getServiceItems());
		this.updatePartItems(entity.getPartItems());
	}

	private void updateAmount() {
		BigDecimal sum = BigDecimal.ZERO;
		for (BudgetItemServiceEntity item : serviceItems) {
			sum = sum.add(item.getTotal());
		}
		for (BudgetItemPartEntity partItem : partItems) {
			sum = sum.add(partItem.getTotal());
		}
		this.amount = sum;
	}

	private void updateServiceItems(List<BudgetItemServiceEntity> newItems) {

		this.serviceItems.removeIf(existingItem ->
				newItems.stream().noneMatch(newItem -> newItem.getId().equals(existingItem.getId()))
		);

		for (BudgetItemServiceEntity newItem : newItems) {

			Optional<BudgetItemServiceEntity> existingItemOptional = this.serviceItems.stream()
					.filter(existingItem -> existingItem.getId().equals(newItem.getId()))
					.findFirst();

			if (existingItemOptional.isPresent()) {
				BudgetItemServiceEntity existingItem = existingItemOptional.get();
				existingItem.update(newItem);
				this.updateAmount();
			} else {
				this.serviceItemIncluding(newItem);
			}
		}

		this.updateAmount();
	}

	public void addServiceItem(BudgetItemServiceEntity serviceItem) {
		this.serviceItemIncluding(serviceItem);
		this.updateAmount();
	}

	private void serviceItemIncluding(BudgetItemServiceEntity serviceItem) {
		serviceItem.getId().setId(getNextServiceId());
		this.serviceItems.add(serviceItem);
	}

	private Long getNextServiceId() {
		return this.serviceItems.isEmpty() ? 1 : this.serviceItems.get(this.serviceItems.size() - 1).getId().getId() + 1;
	}

	//Part items

	private void updatePartItems(List<BudgetItemPartEntity> newItems) {

		this.partItems.removeIf(existingItem ->
				newItems.stream().noneMatch(newItem -> newItem.getId().equals(existingItem.getId()))
		);

		for (BudgetItemPartEntity newItem : newItems) {

			Optional<BudgetItemPartEntity> existingItemOptional = this.partItems.stream()
					.filter(existingItem -> existingItem.getId().equals(newItem.getId()))
					.findFirst();

			if (existingItemOptional.isPresent()) {
				BudgetItemPartEntity existingItem = existingItemOptional.get();
				existingItem.update(newItem);
				this.updateAmount();
			} else {
				this.partItemIncluding(newItem);
			}
		}

		this.updateAmount();
	}

	public void addPartItem(BudgetItemPartEntity partItem) {
		this.partItemIncluding(partItem);
		this.updateAmount();
	}

	private void partItemIncluding(BudgetItemPartEntity partItem) {
		partItem.getId().setItemId(getNextPartId());
		this.partItems.add(partItem);
	}

	private Long getNextPartId() {
		return this.partItems.isEmpty() ? 1 : this.partItems.get(this.partItems.size() - 1).getId().getItemId() + 1;
	}

	public void increaseAmount(BigDecimal value) {
		this.amount = this.amount.add(value);
	}

	public void decreaseAmount(BigDecimal value) {
		this.amount = this.amount.subtract(value);
	}
}
