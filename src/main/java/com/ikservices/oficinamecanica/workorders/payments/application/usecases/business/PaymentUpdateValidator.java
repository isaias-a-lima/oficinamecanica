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
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PaymentUpdateValidator implements IPaymentBusiness {

    private static final String PAID_MSG = "Esta ordem de serviço já está paga e não pode ser alterada.";
    private static final String PAYMENT_AMOUNT_IS_GREATER_THAN_WORK_ORDER_FINAL_VALUE = "A soma de todos os pagamentos não pode ultrapassar o valor final da Ordem de Serviço.";
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

            int lastId = 0;
            boolean lastPaid = false;

            for (Payment payment : paymentList) {
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

                lastId = payment.getId().getNumber();
                lastPaid = null != payment.getPayDate();
            }

            if (aux.compareTo(workOrder.getFinalValue()) < 0) {

                final int finalLastId = lastId;

                if (!lastPaid) {
                    Payment payment = paymentList.stream().filter(p -> p.getId().getNumber() == finalLastId).findFirst().orElseThrow(NoSuchElementException::new);
                    payment.setPaymentValue(workOrder.getFinalValue().subtract(aux));
                    int index = paymentList.indexOf(payment);

                    paymentList.set(index, payment);
                } else {
                    Payment newPayment = new Payment();
                    newPayment.setId(new PaymentId(finalLastId + 1, workOrder.getId().getWorkOrderId(), workOrder.getId().getBudgetId()));
                    newPayment.setDueDate(LocalDate.now());
                    newPayment.setPaymentValue(workOrder.getFinalValue().subtract(aux));
                    newPayment.setPaymentType(PaymentTypeEnum.NONE);

                    paymentList.add(newPayment);
                }
            }
        }
    }

    public static void execute(List<Payment> paymentList) {
        new PaymentUpdateValidator().validate(paymentList);
    }
}
