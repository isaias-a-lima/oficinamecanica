package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.gateways.IWorkOrderBusiness;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.math.BigDecimal;
import java.util.Objects;

public class WorkOrderPaymentsUpdateValidations implements IWorkOrderBusiness {
    private static final String PAID_MSG = "Esta ordem de serviço já está paga e não pode ser alterada.";
    private static final String PAYMENT_AMOUNT_IS_GREATER_THAN_WORK_ORDER_FINAL_VALUE = "A soma de todos os pagamentos não pode ultrapassar o valor final da Ordem de Serviço.";
    private static final String CANNOT_BE_PAID = "Esta ordem de serviço não pode ser paga enquanto não for finalizada.";
    @Override
    public void validate(WorkOrder workOrder) {
        if (Boolean.TRUE.equals(workOrder.getPaid())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), PAID_MSG));
        }

        BigDecimal aux = BigDecimal.ZERO;
        BigDecimal amountPaid = BigDecimal.ZERO;

        for (Payment payment : workOrder.getPayments()) {
            aux = aux.add(payment.getPaymentValue());

            if (aux.compareTo(workOrder.getFinalValue()) > 0) {
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), PAYMENT_AMOUNT_IS_GREATER_THAN_WORK_ORDER_FINAL_VALUE));
            }

            if (Objects.nonNull(payment.getPayDate())) {
                amountPaid = amountPaid.add(payment.getPaymentValue());
            }

            if (!WorkOrderStatusEnum.DONE.equals(workOrder.getWorkOrderStatus()) && amountPaid.compareTo(workOrder.getFinalValue()) == 0) {
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), CANNOT_BE_PAID));
            }
        }
    }

    public static void verify(WorkOrder workOrder) {
        new WorkOrderPaymentsUpdateValidations().validate(workOrder);
    }
}
