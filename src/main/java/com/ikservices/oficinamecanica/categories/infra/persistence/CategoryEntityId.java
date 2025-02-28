package com.ikservices.oficinamecanica.categories.infra.persistence;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CategoryEntityId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORYID")
	private Integer categoryId;
	
	@Column(name = "WORKSHOPID")
	private Long workshopId;
}
