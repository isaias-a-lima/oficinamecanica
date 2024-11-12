package com.ikservices.oficinamecanica.budgets.items.services.domain;

import java.math.BigDecimal;

import com.ikservices.oficinamecanica.services.domain.Service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BudgetItemService {
	Service service;
	Integer quantity;
	BigDecimal discount;
}
