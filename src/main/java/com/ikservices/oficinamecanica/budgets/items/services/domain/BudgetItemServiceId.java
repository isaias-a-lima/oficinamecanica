package com.ikservices.oficinamecanica.budgets.items.services.domain;

import java.io.Serializable;

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
public class BudgetItemServiceId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long budgetId;
}
