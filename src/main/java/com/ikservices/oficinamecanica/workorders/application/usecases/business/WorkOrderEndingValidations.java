package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.workorders.application.gateways.IWorkOrderBusiness;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.PayFormEnum;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.installments.domain.PayTypeEnum;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallment;
import com.ikservices.oficinamecanica.workorders.installments.domain.WorkOrderInstallmentId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkOrderEndingValidations implements IWorkOrderBusiness<WorkOrder> {
    private final static String NULL_AMOUNT = "O valor total não pode ser nulo.";
    private final static String NULL_PAY_FORM = "Escolha uma forma de pagamento.";
    private final static String NULL_PAY_QTY = "Escolha o número de parcelas ou escolha 1 se for pagamento à vista.";
    private final static String EMPTY_LIST = "As parcelas ou pagamento à vista não puderam ser calculados.";
    private final static String FINISHED = "Esta ordem de serviço já está concluída.";
    private final static String CANCELED = "Esta ordem de serviço já está cancelada.";

    @Override
    public void validate(WorkOrder object) {
        PayFormEnum payForm = object.getPayForm();
        Integer payQty = object.getPayQty();

        if (WorkOrderStatusEnum.DONE.equals(object.getWorkOrderStatus()) || WorkOrderStatusEnum.CANCELLED.equals(object.getWorkOrderStatus())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), WorkOrderStatusEnum.DONE.equals(object.getWorkOrderStatus()) ? FINISHED : CANCELED));
        }

        if (Objects.isNull(object.getAmount())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), NULL_AMOUNT));
        }

        if (Objects.isNull(object.getPayForm())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), NULL_PAY_FORM));
        }

        if (Objects.isNull(object.getPayQty())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), NULL_PAY_QTY));
        }
    }

    public static List<WorkOrderInstallment> defineInstallments(WorkOrder workOrder) {

        new WorkOrderEndingValidations().validate(workOrder);

        List<WorkOrderInstallment> list = new ArrayList<>();

        //TODO improve, eliminate duplicated code
        if (PayFormEnum.CREDIT.equals(workOrder.getPayForm()) || PayFormEnum.EXCHANGE.equals(workOrder.getPayForm())) {
            WorkOrderInstallment installment = new WorkOrderInstallment();
            installment.setId(new WorkOrderInstallmentId(1L, workOrder.getId().getWorkOrderId(), workOrder.getId().getBudgetId(), PayTypeEnum.DEFAULT));
            installment.setDueDate(LocalDate.now());
            installment.setPayValue(workOrder.getAmount());

            list.add(installment);

        } else {
            BigDecimal installmentValue = workOrder.getAmount().divide(new BigDecimal(workOrder.getPayQty()), RoundingMode.HALF_UP);
            BigDecimal auxValue = BigDecimal.ZERO;

            for (int i = 1; i <= workOrder.getPayQty(); i++) {
                WorkOrderInstallment installment = new WorkOrderInstallment();
                installment.setId(new WorkOrderInstallmentId((long) i, workOrder.getId().getWorkOrderId(), workOrder.getId().getBudgetId(), PayTypeEnum.INSTALLMENT));
                installment.setDueDate(i == 1 ? LocalDate.now() : LocalDate.now().plusMonths(i - 1));

                if (i == workOrder.getPayQty()) {
                    installment.setPayValue(workOrder.getAmount().subtract(auxValue));
                } else {
                    installment.setPayValue(installmentValue);
                }

                auxValue = auxValue.add(installmentValue);

                list.add(installment);

            }
        }

        if (list.isEmpty()) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), EMPTY_LIST));
        }

        return list;
    }
}
