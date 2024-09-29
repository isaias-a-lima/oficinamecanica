package com.ikservices.oficinamecanica.parts.infra.persistence;

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
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class PartEntityId implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "PARTID")
	private Long id;
	@Column(name = "WORkSHOPID")
	private Long workshopId;
}
