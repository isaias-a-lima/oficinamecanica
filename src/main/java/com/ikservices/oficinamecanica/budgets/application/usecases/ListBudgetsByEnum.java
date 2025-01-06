package com.ikservices.oficinamecanica.budgets.application.usecases;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;

public enum ListBudgetsByEnum {
    WORKSHOP,
    VEHICLE,
    CUSTOMER;

    public static ListBudgetsByEnum findByIndex(int index) {
        for (ListBudgetsByEnum value : ListBudgetsByEnum.values()) {
            if (value.ordinal() == index) {
                return value;
            }
        }
        throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), String.format("Option %d not found", index)));
    }
}
