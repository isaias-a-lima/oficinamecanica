package com.ikservices.oficinamecanica.workorders.payments.application.gateways;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.payments.application.enumerates.PaymentStateEnum;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository {
    List<Payment> listPayments(WorkOrderId workOrderId);
    List<Payment> listOverduePayments(Long workshopId);
    List<Payment> listPaymentsByDuePeriod(Long workshopId, 
    		LocalDate dueDateBegin, LocalDate dueDateEnd,
    		PaymentStateEnum paymentState);
    Payment getPayment(PaymentId id);
}
