package com.ikservices.oficinamecanica.work.orders.domain;

public enum WorkOrderStatusEnum {
	QUEUE,
	PROGRESS,
	PARTS,
	DONE,
	CANCELLED;
	
	public static WorkOrderStatusEnum findByIndex(int index) {
		for(WorkOrderStatusEnum value : WorkOrderStatusEnum.values()) {
			if(value.ordinal() == index) {
				return value;
			}
		}
		
		return null;
	}
}
