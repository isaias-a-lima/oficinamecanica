package com.ikservices.oficinamecanica.inventory.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class MovementEntityId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "WORKSHOPID")
	private Long workshopId;
	
	@Column(name = "INVENTORYID")
	private Long inventoryId;
}
