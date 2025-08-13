package com.ikservices.oficinamecanica.inventory.infra.controller;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {
		"workshopId",
		"inventoryId"
})
public class MovementRequestDTO {
	private Long workshopId;
	private Long inventoryId;
	private LocalDate movementDate;
	private Integer movementType;
	private String documentNumber;
	private Integer supplierId;
	private Integer partId;
	private Integer quantity;
	private Integer source;
}
