package com.ikservices.oficinamecanica.inventory.domain.enumerates;

import java.util.Objects;

public enum SourceEnum {
	service,
	purchase,
	adjustment,
	NONE;
	
	public static SourceEnum findByIndex(Integer index) {
		if(Objects.nonNull(index)) {
			for(SourceEnum value : SourceEnum.values()) {
				if(value.ordinal() == index) {
					return value;
				}
			}
		}
		
		return NONE;
	}
}
