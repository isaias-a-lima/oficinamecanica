package com.ikservices.oficinamecanica.payables.application.usecases;

import com.ikservices.oficinamecanica.commons.interfaces.IKBusiness;
import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.business.UpdateRules;

import java.util.ArrayList;
import java.util.List;

public class UpdatePayable {
	private final PayableRepository repository;
	private final List<IKBusiness<Payable>> businessList;
	
	public UpdatePayable(PayableRepository repository) {
		this.repository = repository;

		this.businessList = new ArrayList<>();
		this.businessList.add(new UpdateRules());
    }
	
	public Payable execute(Payable payable) {
		this.businessList.forEach(rule -> rule.execute(payable));
		return repository.updatePayable(payable);
	}
}
