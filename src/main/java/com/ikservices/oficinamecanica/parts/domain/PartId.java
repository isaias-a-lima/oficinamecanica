package com.ikservices.oficinamecanica.parts.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PartId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long workshopId;
}
