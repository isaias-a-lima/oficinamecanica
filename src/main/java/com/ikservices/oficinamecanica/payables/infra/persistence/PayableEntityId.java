package com.ikservices.oficinamecanica.payables.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PayableEntityId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "WORKSHOPID")
	private Long workshopId;
}
