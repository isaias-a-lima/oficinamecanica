package com.ikservices.oficinamecanica.workorders.payments.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.payments.application.gateways.IPaymentBusiness;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PaymentUpdateValidator implements IPaymentBusiness {

    private static final String PAID_MSG = "Esta ordem de serviço já está paga e não pode ser alterada.";
    private static final String PAYMENT_AMOUNT_IS_GREATER_THAN_WORK_ORDER_AMOUNT = "A soma de todos os pagamentos não pode ultrapassar o valor total da Ordem de Serviço.";
    private static final String CANNOT_BE_PAID = "Esta ordem de serviço não pode ser totalmente paga enquanto não for finalizada.";

    @Override
    public void validate(Payment payment) {

    }

    @Override
    public void validate(List<Payment> paymentList) {

        if (Objects.isNull(paymentList)) {
            throw new IKException((new IKMessage(IKConstants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), IKConstants.NULL_OBJECT_MESSAGE)));
        }

        if (!paymentList.isEmpty()) {
            WorkOrder workOrder = paymentList.get(0).getWorkOrder();

            if (workOrder.getPaid()) {
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), PAID_MSG));
            }

            BigDecimal aux = BigDecimal.ZERO;
            BigDecimal amountPaid = BigDecimal.ZERO;

            for (Payment payment : paymentList) {
                aux = aux.add(payment.getPaymentValue());

                if (aux.compareTo(workOrder.getAmount()) > 0) {
                    throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), PAYMENT_AMOUNT_IS_GREATER_THAN_WORK_ORDER_AMOUNT));
                }

                if (Objects.nonNull(payment.getPayDate())) {
                    amountPaid = amountPaid.add(payment.getPaymentValue());
                }

                if (!WorkOrderStatusEnum.DONE.equals(workOrder.getWorkOrderStatus()) && amountPaid.compareTo(workOrder.getAmount()) == 0) {
                    throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), CANNOT_BE_PAID));
                }
            }
        }
    }

    public static void execute(List<Payment> paymentList) {
        new PaymentUpdateValidator().validate(paymentList);
    }
}
