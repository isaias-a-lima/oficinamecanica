package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;

public class GetPayable {
	private PayableRepository repository;
	
	public GetPayable(PayableRepository repository) {
		this.repository = repository;
	}
	
	public Payable execute(PayableId id) {
		return repository.getPayable(id);
	}
}
