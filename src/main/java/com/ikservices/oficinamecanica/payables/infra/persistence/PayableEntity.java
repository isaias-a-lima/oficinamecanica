package com.ikservices.oficinamecanica.payables.infra.persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.categories.infra.persistence.CategoryEntity;

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
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CATEGORYID" , referencedColumnName = "CATEGORYID", updatable = false, insertable = false),
		@JoinColumn(name = "WORKSHOPID", referencedColumnName = "WORKSHOPID", updatable = false, insertable = false)
	})
	private CategoryEntity category;
	
	@Column(name = "CREATION")
	private LocalDate creationDate;
	
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

	@Column(name = "NOTE")
	private String note;
	
	public void update(PayableEntity entity) {
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
		if(Objects.nonNull(entity.getNote())) {
			this.note = entity.getNote();
		}
	}
}
