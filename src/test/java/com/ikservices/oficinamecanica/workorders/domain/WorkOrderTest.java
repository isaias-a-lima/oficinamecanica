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

        WorkOrderPartItem partItem = new WorkOrderPartItem();
        partItem.setId(new WorkOrderPartItemId(1L, 1L, 1L));
        partItem.setItemValue(new BigDecimal("3595.00"));
        partItem.setServiceCost(BigDecimal.ZERO);
        partItem.setDiscount(BigDecimal.ZERO);
        partItem.setQuantity(1);

        subject.setPartItems(Collections.singletonList(partItem));
    }

    @Test
    public void testGetFinalValue() {
        subject.setDiscount(new BigDecimal("2.6425"));
        BigDecimal expected = new BigDecimal("3500.00");
        Assertions.assertEquals(expected, subject.getFinalValue());
    }

    @Test
    public void testGetFinalValue_ThrowExcessiveDiscount() {
        subject.setDiscount(new BigDecimal("110"));
        Assertions.assertThrows(IKException.class, ()-> subject.getFinalValue());
    }
}
