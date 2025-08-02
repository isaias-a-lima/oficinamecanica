package com.ikservices.oficinamecanica.workorders.payments.application.gateways;

import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;

import java.util.List;

public interface IPaymentBusiness {
    void validate(Payment payment);
    void validate(List<Payment> paymentList);
}
