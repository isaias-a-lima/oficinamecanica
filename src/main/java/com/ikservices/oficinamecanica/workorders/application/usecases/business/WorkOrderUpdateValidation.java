package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.gateways.IWorkOrderBusiness;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;

public class WorkOrderUpdateValidation implements IWorkOrderBusiness {
    private static final String DONE_OR_CANCELLED_MSG = "Esta ordem de serviço já está %s";
    @Override
    public void validate(WorkOrder workOrder) {
        if (WorkOrderStatusEnum.DONE.equals(workOrder.getWorkOrderStatus()) || WorkOrderStatusEnum.CANCELLED.equals(workOrder.getWorkOrderStatus())) {
            String param = WorkOrderStatusEnum.DONE.equals(workOrder.getWorkOrderStatus()) ? "concluída" : "cancelada";
            String str = String.format(DONE_OR_CANCELLED_MSG, param);
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), str));
        }
    }

    public static void verify(WorkOrder workOrder) {
        new WorkOrderUpdateValidation().validate(workOrder);
    }
}
