package com.ikservices.oficinamecanica.services.infra.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class ServiceEntityId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SERVICEID")
	private Long id;
	@Column(name = "WORKSHOPID")
	private Long workshopId;
}
