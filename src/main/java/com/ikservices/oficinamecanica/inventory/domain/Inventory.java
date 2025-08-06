package com.ikservices.oficinamecanica.inventory.domain;

import java.time.LocalDate;

import com.ikservices.oficinamecanica.inventory.domain.enumerates.MovementTypeEnum;
import com.ikservices.oficinamecanica.inventory.domain.enumerates.SourceEnum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Inventory {
	private InventoryId id;
	private LocalDate movementDate;
	private MovementTypeEnum movementType;
	private String documentNumber;
	private Integer supplierId;
	private Integer partId;
	private Integer quantity;
	private SourceEnum sourceEnum;
}
