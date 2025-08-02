package com.ikservices.oficinamecanica.workorders.application.usecases.business;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;
import com.ikservices.oficinamecanica.workorders.domain.enumarates.WorkOrderStatusEnum;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItemId;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItem;
import com.ikservices.oficinamecanica.workorders.items.services.domain.WorkOrderServiceItemId;
import com.ikservices.oficinamecanica.workorders.payments.domain.Payment;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentId;
import com.ikservices.oficinamecanica.workorders.payments.domain.PaymentTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorkOrderPaymentsUpdateValidationsTest {
    private WorkOrderPaymentsUpdateValidations subject;
    private WorkOrder workOrder;
    private static final Long WORK_ORDER_ID = 1L;
    private static final Long WORKSHOP_ID = 1L;
    private static final Long BUDGET_ID = 1L;

    @BeforeEach
    public void setup() {
        subject = new WorkOrderPaymentsUpdateValidations();

        workOrder = getWorkOrder(WORK_ORDER_ID, BUDGET_ID);

        List<WorkOrderServiceItem> serviceItems = Collections.singletonList(getServiceItem(1L, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(100)));
        workOrder.setServiceItems(serviceItems);

        List<WorkOrderPartItem> partItems = Collections.singletonList(getPartItem(1L, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(100), BigDecimal.valueOf(100)));
        workOrder.setPartItems(partItems);
    }

    @Test
    public void TestIfThePaymentAmountIsGreaterThanTheWorkOrderAmountRaiseAnException() {

        workOrder.getPayments().add(getPayment(1, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(100), true));

        workOrder.getPayments().add(getPayment(2, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(250), true));

        Assertions.assertThrows(IKException.class, () -> subject.validate(workOrder));
    }

    @Test
    public void TestIfThePaymentAmountIsLessThanTheWorkOrderAmountDoNotRaiseAnException() {

        workOrder.getPayments().add(getPayment(1, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(50), true));

        Assertions.assertDoesNotThrow(() -> subject.validate(workOrder));
    }

    @Test
    public void TestIfThePaymentAmountIsEqualToTheWorkOrderAmountRaiseAnException() {

        workOrder.getPayments().add(getPayment(1, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(150), true));

        workOrder.getPayments().add(getPayment(2, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(150), true));

        Assertions.assertThrows(IKException.class, () -> subject.validate(workOrder));
    }



    @Test
    public void TestIfThePaymentAmountIsEqualToTheWorkOrderAmountButItIsNotPaidDoNotRaiseAnException() {

        workOrder.getPayments().add(getPayment(1, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(50), false));

        workOrder.getPayments().add(getPayment(2, WORK_ORDER_ID, BUDGET_ID, BigDecimal.valueOf(150), false));

        Assertions.assertDoesNotThrow(() -> subject.validate(workOrder));
    }

    private WorkOrder getWorkOrder(Long workOrderId, Long budgetId) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(new WorkOrderId(workOrderId, budgetId));
        workOrder.setBudget(null);
        workOrder.setOpeningDate(LocalDate.now());
        workOrder.setKm(15000L);
        workOrder.setWorkOrderStatus(WorkOrderStatusEnum.QUEUE);
        workOrder.setPayQty(null);
        workOrder.setPaid(null);
        workOrder.setServiceItems(new ArrayList<>());
        workOrder.setPartItems(new ArrayList<>());
        workOrder.setPayments(new ArrayList<>());
        return workOrder;
    }

    private WorkOrderServiceItem getServiceItem(Long itemId, Long workOrderId, Long budgetId, BigDecimal itemValue) {
        WorkOrderServiceItem serviceItem = new WorkOrderServiceItem();
        serviceItem.setItemId(new WorkOrderServiceItemId(itemId, workOrderId, budgetId));
        serviceItem.setService(new Service(new ServiceId(1L, WORKSHOP_ID)));
        serviceItem.setQuantity(1);
        serviceItem.setItemValue(itemValue);
        serviceItem.setDiscount(BigDecimal.ZERO);
        return serviceItem;
    }

    private WorkOrderPartItem getPartItem(Long itemId, Long workOrderId, Long budgetId, BigDecimal itemValue, BigDecimal serviceCost) {
        WorkOrderPartItem partItem = new WorkOrderPartItem();
        partItem.setId(new WorkOrderPartItemId(itemId, workOrderId, budgetId));
        partItem.setPart(new Part(new PartId(1L, 1L)));
        partItem.setQuantity(1);
        partItem.setItemValue(itemValue);
        partItem.setServiceCost(serviceCost);
        partItem.setDiscount(BigDecimal.ZERO);
        return partItem;
    }

    private Payment getPayment(Integer number, Long workOrderId, Long budgetId, BigDecimal value, boolean paid) {
        Payment payment = new Payment();
        payment.setId(new PaymentId(number, workOrderId, budgetId));
        payment.setDueDate(LocalDate.now());
        payment.setPaymentValue(value);
        payment.setPaymentType(PaymentTypeEnum.DEBIT);
        payment.setNote("Test");
        payment.setPayDate(paid ? LocalDate.now() : null);
        return payment;
    }
}
