package com.ikservices.oficinamecanica.work.orders.domain;

public enum PayFormEnum {
	DEBIT,
	CREDIT,
	CASH,
	CHECK,
	EXCHANGE;
	
	public static PayFormEnum findByIndex(int index) {
		for(PayFormEnum value : PayFormEnum.values()) {
			if(value.ordinal() == index) {
				return value;
			}
		}
		
		return null;
	}
}
