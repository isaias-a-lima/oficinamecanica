package com.ikservices.oficinamecanica.workorders.infra.persistence;

import java.io.Serializable;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;

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
public class WorkOrderEntityId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long workshopId;
	private Long workOrderId;
}
