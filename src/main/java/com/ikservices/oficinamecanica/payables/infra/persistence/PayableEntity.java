package com.ikservices.oficinamecanica.payables.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "PAYABLE")
public class PayableEntity {
	@EmbeddedId
	private PayableEntityId id;
	
	@Column(name = "CREATION")
	private LocalDate creation;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DOCNUMBER")
	private String docNumber;
	
	@Column(name = "DUEDATE")
	private LocalDate dueDate;
	
	@Column(name = "PAYVALUE")
	private BigDecimal payValue;
	
	@Column(name = "PAYDATE")
	private LocalDate payDate;
	
	@Column(name = "CATEGORYID")
	private Integer categoryId;
	
	public void update(PayableEntity entity) {
		if(Objects.nonNull(entity.getCreation())) {
			this.creation = entity.getCreation();
		}
		if(Objects.nonNull(entity.getDescription())) {
			this.description = entity.getDescription();
		}
		if(Objects.nonNull(entity.getDocNumber())) {
			this.docNumber = entity.getDocNumber();
		}
		if(Objects.nonNull(entity.getDueDate())) {
			this.dueDate = entity.getDueDate();
		}
		if(Objects.nonNull(entity.getPayValue())) {
			this.payValue = entity.getPayValue();
		}
		if(Objects.nonNull(entity.getPayDate())) {
			this.payDate = entity.getPayDate();
		}
		if(Objects.nonNull(entity.getCategoryId())) {
			this.categoryId = entity.getCategoryId();
		}
	}
}
