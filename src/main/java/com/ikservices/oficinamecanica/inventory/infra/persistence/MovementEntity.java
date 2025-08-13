package com.ikservices.oficinamecanica.inventory.infra.persistence;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "INVENTORY_MOV")
public class MovementEntity {
	@Column(name="WORKSHOPID")
	private Long workshopId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="INVENTORYID")
	private Long inventoryId;
	
	@Column(name="MOVDATE")
	private LocalDate movementDate;
	
	@Column(name="MOVTYPE")
	private MovementTypeEnum movementType;
	
	@Column(name="DOCNUMBER")
	private String documentNumber;
	
	@Column(name="SUPPLIERID")
	private Integer supplierId;
	
	@Column(name="PARTID")
	private Integer partId;
	
	@Column(name="QTY")
	private Integer quantity;
	
	@Column(name="SOURCE")
	private SourceEnum source;
	
	public void update(MovementEntity entity) {
		if(Objects.nonNull(entity.getMovementType())) {
			this.movementType = entity.movementType;
		}
		if(Objects.nonNull(entity.getDocumentNumber())) {
			this.documentNumber = entity.getDocumentNumber();
		}
		if(Objects.nonNull(entity.getSupplierId())) {
			this.supplierId = entity.getSupplierId();
		}
		if(Objects.nonNull(entity.getPartId())) {
			this.partId = entity.getPartId();
		}
		if(Objects.nonNull(entity.getQuantity())) {
			this.quantity = entity.getQuantity();
		}
		if(Objects.nonNull(entity.getSource())) {
			this.source = entity.getSource();
		}
	}
}
