package com.ikservices.oficinamecanica.budgets.items.services.infra.persistence;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;

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
@Table(name = "BUDG_ITEM_SERV")
public class BudgetItemServiceEntity {
	@EmbeddedId
	private BudgetItemServiceEntityId id;
	
	@ManyToOne
	@JoinColumn(name = "BUDGETID", referencedColumnName = "BUDGETID", updatable = false, insertable = false)
	private BudgetEntity budgetEntity;

	@Column(name = "SERVICEID")
	private Long serviceId;
	
	@OneToOne
	private ServiceEntity serviceEntity;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "DISCOUNT")
	private BigDecimal discount;
	
	public void update(BudgetItemServiceEntity entity) {
		if(Objects.nonNull(entity.getQuantity())) {
			this.quantity = entity.getQuantity();
		}
		if(Objects.nonNull(entity.getCost())) {
			this.cost = entity.getCost();
		}
		if(Objects.nonNull(entity.getDiscount())) {
			this.discount = entity.getDiscount();
		}
	}
}
