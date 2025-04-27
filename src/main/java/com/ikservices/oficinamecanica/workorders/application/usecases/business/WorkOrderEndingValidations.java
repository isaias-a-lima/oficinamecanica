package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.gateways.IWorkOrderBusiness;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkOrderEndingValidations implements IWorkOrderBusiness {
    private final static String NULL_AMOUNT = "O valor total não pode ser nulo.";
    private final static String NULL_PAY_FORM = "Escolha uma forma de pagamento.";
    private final static String NULL_PAY_QTY = "Escolha o número de parcelas ou escolha 1 se for pagamento à vista.";
    private final static String EMPTY_LIST = "As parcelas ou pagamento à vista não puderam ser calculados.";
    private final static String FINISHED = "Esta ordem de serviço já está concluída.";
    private final static String CANCELED = "Esta ordem de serviço já está cancelada.";

    @Override
    public void validate(WorkOrder object) {

        if (WorkOrderStatusEnum.DONE.equals(object.getWorkOrderStatus()) || WorkOrderStatusEnum.CANCELLED.equals(object.getWorkOrderStatus())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), WorkOrderStatusEnum.DONE.equals(object.getWorkOrderStatus()) ? FINISHED : CANCELED));
        }

        if (Objects.isNull(object.getAmount())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), NULL_AMOUNT));
        }
    }

    public static List<Payment> definePayments(WorkOrder workOrder) {

        new WorkOrderEndingValidations().validate(workOrder);

        List<Payment> list = new ArrayList<>();

        if (Objects.nonNull(workOrder.getPayQty()) && workOrder.getPayQty() > 0) {
            BigDecimal paymentValue = workOrder.getAmount().divide(new BigDecimal(workOrder.getPayQty()), RoundingMode.HALF_UP);
            BigDecimal auxValue = BigDecimal.ZERO;

            for (int i = 1; i <= workOrder.getPayQty(); i++) {
                Payment payment = new Payment();
                payment.setId(new PaymentId(i, workOrder.getId().getWorkOrderId(), workOrder.getId().getBudgetId()));
                payment.setDueDate(i == 1 ? LocalDate.now() : LocalDate.now().plusMonths(i - 1));

                if (i == workOrder.getPayQty()) {
                    payment.setPaymentValue(workOrder.getAmount().subtract(auxValue));
                } else {
                    payment.setPaymentValue(paymentValue);
                }

                auxValue = auxValue.add(paymentValue);

                list.add(payment);
            }

            return list;
        }

        //If a payQty value was not defined, a unique payment will be defined.
        Payment payment = new Payment();
        payment.setId(new PaymentId(1, workOrder.getId().getWorkOrderId(), workOrder.getId().getBudgetId()));
        payment.setDueDate(LocalDate.now());
        payment.setPaymentValue(workOrder.getAmount());

        list.add(payment);

        return list;
    }
}
