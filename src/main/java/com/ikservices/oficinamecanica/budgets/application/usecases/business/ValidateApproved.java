package com.ikservices.oficinamecanica.budgets.application.usecases.business;

import com.ikservices.oficinamecanica.budgets.application.gateways.IBudgetBusiness;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;

public class ValidateApproved extends IBudgetBusiness<Budget> {

    private static final String ALREADY_APPROVED = "Orçamento já está aprovado.";

    @Override
    public void validate(Budget object) {
        if (BudgetStatusEnum.APPROVED.equals(object.getBudgetStatus())) {
            throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), ALREADY_APPROVED));
        }
    }

    public static void execute(Budget budget) {
        new ValidateApproved().validate(budget);
    }
}
