package com.ikservices.oficinamecanica.workorders.payments.application.enumerates;

public enum PaymentStateEnum {
	NONE,
	PAID,
	UNPAID;
	
	public static PaymentStateEnum findByIndex(int index) {
		for (PaymentStateEnum value : PaymentStateEnum.values()) {
			if(value.ordinal() == index) {
				return value;
			}
		}
		
		return PaymentStateEnum.NONE;
	}
}
