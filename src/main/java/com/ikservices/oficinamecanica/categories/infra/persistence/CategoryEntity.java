package com.ikservices.oficinamecanica.categories.infra.persistence;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ikservices.oficinamecanica.categories.domain.CategoryTypeEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "MOV_CATEGORY")
public class CategoryEntity {
	@EmbeddedId
	private CategoryEntityId id; 
	
	@Column(name = "CREATION")
	private LocalDate creation;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CATEGTYPE")
	private CategoryTypeEnum categType;
	
	@Column(name = "ACTIVATED")
	private Boolean activated;
	
	public void update(CategoryEntity entity) {
		if(Objects.nonNull(entity.getCreation())) {
			this.creation = entity.getCreation();
		}
		if(Objects.nonNull(entity.getDescription())) {
			this.description = entity.getDescription();
		}
		if(Objects.nonNull(entity.getCategType())) {
			this.categType = entity.getCategType();
		}
		if(Objects.nonNull(entity.getActivated())) {
			this.activated = entity.getActivated();
		}
	}
}
