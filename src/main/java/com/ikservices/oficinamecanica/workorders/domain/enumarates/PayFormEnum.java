package com.ikservices.oficinamecanica.workorders.domain.enumarates;

public enum PayFormEnum {
	DEBIT,
	CREDIT,
	CASH,
	PIX,
	INVOICE,
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
