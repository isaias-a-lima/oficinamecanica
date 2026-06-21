package com.ikservices.oficinamecanica.workorders.domain;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItem;
import com.ikservices.oficinamecanica.workorders.items.parts.domain.WorkOrderPartItemId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        this.setupSubject(new BigDecimal("3595.00"), BigDecimal.ZERO, new BigDecimal("2.6425"), false);
        BigDecimal expected = new BigDecimal("3500.00");
        Assertions.assertEquals(expected, subject.getFinalValue());

        this.setupSubject(new BigDecimal("15150.00"), new BigDecimal(150), BigDecimal.ZERO, true);
        expected = new BigDecimal("15000.00");
        Assertions.assertEquals(expected, subject.getFinalValue());
    }

    @Test
    public void testGetFinalValue_ThrowExcessiveDiscount() {
        this.setupSubject(new BigDecimal("15100.00"), BigDecimal.ZERO, new BigDecimal("110"), false);
        Assertions.assertThrows(IKException.class, ()-> subject.getFinalValue());
    }

    @Test
    public void testRoundedFinalValue() {
        this.setupSubject(new BigDecimal("15100.00"), BigDecimal.ZERO, new BigDecimal("0.6623"), true);
        BigDecimal expected = new BigDecimal("15000.00");
        Assertions.assertEquals(expected, subject.getFinalValue());
    }

    @Test
    public void testGetMonetaryFormatDiscount() {
        this.setupSubject(BigDecimal.valueOf(1150), BigDecimal.ZERO, BigDecimal.TEN, false);
        Assertions.assertEquals(BigDecimal.valueOf(115).setScale(2, RoundingMode.HALF_UP), this.subject.getMonetaryFormatDiscount());

        this.setupSubject(BigDecimal.valueOf(1150), BigDecimal.valueOf(115), BigDecimal.ZERO, false);
        Assertions.assertEquals(BigDecimal.valueOf(115).setScale(2, RoundingMode.HALF_UP), this.subject.getMonetaryFormatDiscount());

        this.setupSubject(BigDecimal.valueOf(500), BigDecimal.ZERO, BigDecimal.valueOf(5.11), false);
        Assertions.assertEquals(BigDecimal.valueOf(25.55).setScale(2, RoundingMode.HALF_UP), this.subject.getMonetaryFormatDiscount());

        this.setupSubject(BigDecimal.valueOf(500), BigDecimal.valueOf(25.55), BigDecimal.ZERO, false);
        Assertions.assertEquals(BigDecimal.valueOf(25.55).setScale(2, RoundingMode.HALF_UP), this.subject.getMonetaryFormatDiscount());
    }

    private void setupSubject(BigDecimal itemValue, BigDecimal generalDiscountValue, BigDecimal generalDiscount, boolean isFinalValueRounded) {
        WorkOrderPartItem partItem = new WorkOrderPartItem();
        partItem.setId(new WorkOrderPartItemId(1L, 1L, 1L));
        partItem.setItemValue(itemValue);
        partItem.setServiceCost(BigDecimal.ZERO);
        partItem.setDiscountValue(BigDecimal.ZERO);
        partItem.setDiscount(BigDecimal.ZERO);
        partItem.setQuantity(1);
        subject.setPartItems(Collections.singletonList(partItem));
        subject.setDiscountValue(generalDiscountValue);
        subject.setDiscount(generalDiscount);
        subject.setIsFinalValueRounded(isFinalValueRounded);
    }
}
