package com.ikservices.oficinamecanica.budgets.domain;

public enum BudgetStatusEnum {
	ACTIVE,
	APPROVED,
	EXPIRED,
	CANCELLED;
	
	public static BudgetStatusEnum findByIndex(int index) {
		for(BudgetStatusEnum value : BudgetStatusEnum.values()) {
			if(value.ordinal() == index) {
				return value;
			}
		}
		
		return null;
	}
}
