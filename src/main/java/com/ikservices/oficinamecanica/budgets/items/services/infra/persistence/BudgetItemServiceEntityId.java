package com.ikservices.oficinamecanica.budgets.items.services.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class BudgetItemServiceEntityId implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "SERVICEITEMID")
	private Long id;
	@Column(name = "BUDGETID")
	private Long budgetId;
}
