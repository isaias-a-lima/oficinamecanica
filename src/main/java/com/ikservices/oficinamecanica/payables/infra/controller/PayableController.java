package com.ikservices.oficinamecanica.payables.infra.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.payables.application.usecases.GetNextPayableId;
import com.ikservices.oficinamecanica.payables.application.usecases.GetPayable;
import com.ikservices.oficinamecanica.payables.application.usecases.ListPayable;
import com.ikservices.oficinamecanica.payables.application.usecases.SavePayable;
import com.ikservices.oficinamecanica.payables.application.usecases.UpdatePayable;
import com.ikservices.oficinamecanica.payables.infra.PayableConverter;

@RestController
@RequestMapping("payables")
public class PayableController {
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PayableController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Lazy
	private PayableConverter converter;
	
	private ListPayable listPayable;
	private GetPayable getPayable;
	private SavePayable savePayable;
	private GetNextPayableId getNextPayableId;
	private UpdatePayable updatePayable;
	
	public PayableController(ListPayable listPayable, GetPayable getPayable,
			SavePayable savePayable, GetNextPayableId getNextPayableId) {
		this.listPayable = listPayable;
		this.getPayable = getPayable;
		this.savePayable = savePayable;
		this.getNextPayableId = getNextPayableId;
		this.updatePayable = updatePayable;
	}
}
