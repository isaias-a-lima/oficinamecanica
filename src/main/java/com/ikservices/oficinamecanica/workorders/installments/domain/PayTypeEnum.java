package com.ikservices.oficinamecanica.workorders.installments.domain;

public enum PayTypeEnum {
	DEFAULT,
	INTEREST;
	
	public PayTypeEnum findById(int index) {
		for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
			if (payTypeEnum.ordinal() == index) {
				return payTypeEnum;
			}
		}
		return null;
	}

}
