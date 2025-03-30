package com.ikservices.oficinamecanica.budgets.application.usecases.business;

import com.ikservices.oficinamecanica.budgets.application.gateways.IBudgetBusiness;
import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;

import java.util.Map;

public class BudgetBusiness extends IBudgetBusiness<Map<Long, Budget>>{

    private static final String ALREADY_APPROVED = "Orçamento %s já está aprovado.";

    @Override
    public void validate(Map<Long, Budget> object) {
        for (Long budgetId : object.keySet()) {

            Budget budget = object.get(budgetId);

            if (BudgetStatusEnum.APPROVED.equals(budget.getBudgetStatus())) {
                String message = String.format(ALREADY_APPROVED, budgetId);
                throw new IKException(new IKMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING.getCode(), message));
            }
        }
    }
}
