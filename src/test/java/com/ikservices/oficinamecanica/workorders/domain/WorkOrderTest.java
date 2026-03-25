package com.ikservices.oficinamecanica.workorders.domain;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

public class WorkOrderTest {


    private WorkOrder subject;

    @BeforeEach
    public void setup() {
        subject = new WorkOrder();
        subject.setId(new WorkOrderId(1L, 1L));
    }

    @Test
    public void testGetFinalValue() {
        this.setupSubject(new BigDecimal("3595.00"), new BigDecimal("2.6425"), false);
        BigDecimal expected = new BigDecimal("3500.00");
        Assertions.assertEquals(expected, subject.getFinalValue());
    }

    @Test
    public void testGetFinalValue_ThrowExcessiveDiscount() {
        this.setupSubject(new BigDecimal("15100.00"), new BigDecimal("110"), false);
        Assertions.assertThrows(IKException.class, ()-> subject.getFinalValue());
    }

    @Test
    public void testRoundedFinalValue() {
        this.setupSubject(new BigDecimal("15100.00"), new BigDecimal("0.6623"), true);
        BigDecimal expected = new BigDecimal("15000.00");
        Assertions.assertEquals(expected, subject.getFinalValue());
    }

    private void setupSubject(BigDecimal itemValue, BigDecimal generalDiscount, boolean isFinalValueRounded) {
        WorkOrderPartItem partItem = new WorkOrderPartItem();
        partItem.setId(new WorkOrderPartItemId(1L, 1L, 1L));
        partItem.setItemValue(itemValue);
        partItem.setServiceCost(BigDecimal.ZERO);
        partItem.setDiscount(BigDecimal.ZERO);
        partItem.setQuantity(1);
        subject.setPartItems(Collections.singletonList(partItem));
        subject.setDiscount(generalDiscount);
        subject.setIsFinalValueRounded(isFinalValueRounded);
    }
}
