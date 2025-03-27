package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.gateways.IWorkOrderBusiness;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;

public class WorkOrderUpdatePaymentsValidation implements IWorkOrderBusiness {
    private static final String PAID_MSG = "Esta ordem de serviço já está paga";
    @Override
    public void validate(WorkOrder workOrder) {
        if (Boolean.TRUE.equals(workOrder.getPaid())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), PAID_MSG));
        }
    }

    public static void verify(WorkOrder workOrder) {
        new WorkOrderUpdatePaymentsValidation().validate(workOrder);
    }
}
