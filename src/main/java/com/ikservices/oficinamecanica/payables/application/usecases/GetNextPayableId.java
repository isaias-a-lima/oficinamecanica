package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;

public class GetNextPayableId {
	private PayableRepository repository;
	
	public GetNextPayableId(PayableRepository repository) {
		this.repository = repository;
	}
	
	public Integer execute(Long workshopId) {
		return this.repository.getNextPayableId(workshopId);
	}
}
