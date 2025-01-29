package com.ikservices.oficinamecanica.budgets.domain;

import com.ikservices.oficinamecanica.budgets.items.services.domain.BudgetItemService;
import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Budget {
	Vehicle vehicle;
	LocalDate openingDate;
	Long km;
	BudgetStatusEnum budgetStatus;
	BigDecimal amount;
	List<BudgetItemService> serviceItems;

	public String sumServiceItems() {
		BigDecimal sum = BigDecimal.ZERO;
		if (Objects.nonNull(serviceItems) && !serviceItems.isEmpty()) {
			for (BudgetItemService serviceItem : serviceItems) {
				sum = sum.add(serviceItem.getTotal());
			}
		}
		return NumberUtil.parseStringMoney(sum);
	}
}
