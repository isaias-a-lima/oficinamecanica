package com.ikservices.oficinamecanica.payables.application.gateways;

import java.time.LocalDate;
import java.util.List;

import com.ikservices.oficinamecanica.payables.application.enumerates.PayableStateEnum;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;

public interface PayableRepository {
	List<Payable> listPayables(Long workshopId, LocalDate startDate, LocalDate endDate, PayableStateEnum payableState);
	Payable getPayable(PayableId id);
	Payable savePayable(Payable payable);
	Payable updatePayable(Payable payable);
	Integer getNextPayableId(Long workshopId);
}
