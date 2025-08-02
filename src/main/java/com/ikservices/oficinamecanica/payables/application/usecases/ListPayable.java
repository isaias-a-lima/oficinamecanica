package com.ikservices.oficinamecanica.payables.application.usecases;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.payables.application.enumerates.PayableStateEnum;
import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;

public class ListPayable {
	private PayableRepository repository;
	
	public ListPayable(PayableRepository repository) {
		this.repository = repository;
	}
	 
	public List<Payable> execute(Long workshopId, LocalDate startDate, LocalDate endDate, PayableStateEnum payableState) {
		return repository.listPayables(workshopId, startDate, endDate, payableState);
	}
}
