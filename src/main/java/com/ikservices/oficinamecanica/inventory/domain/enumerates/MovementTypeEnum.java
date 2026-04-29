package com.ikservices.oficinamecanica.inventory.domain.enumerates;

import java.util.Objects;

public enum MovementTypeEnum {
	OUTPUT,
	INPUT,
	FINAL_BALANCE;
	
	public static MovementTypeEnum findByIndex(Integer index) {
		if(Objects.nonNull(index)) {
			for(MovementTypeEnum value : MovementTypeEnum.values()) {
				if(value.ordinal() == index) {
					return value;
				}
			}
		}
		
		return null;
	}
}
