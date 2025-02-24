package com.ikservices.oficinamecanica.payables.application.gateways;

import java.util.List;

import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;

public interface PayableRepository {
	List<Payable> listPayables(Long workshopId);
	Payable getPayable(PayableId id);
	Payable savePayable(Payable payable);
	Payable updatePayable(Payable payable);
}
