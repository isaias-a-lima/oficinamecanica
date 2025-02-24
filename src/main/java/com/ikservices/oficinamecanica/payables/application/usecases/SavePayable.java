package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;

public class SavePayable {
	private PayableRepository repository;
	
	public SavePayable(PayableRepository repository) {
		this.repository = repository;
	}
	
	public Payable execute(Payable payable) {
		return repository.savePayable(payable);
	}
}
