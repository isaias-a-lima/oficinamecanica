package com.ikservices.oficinamecanica.budgets.application.gateways;

public abstract class IBudgetBusiness<T> {
    public abstract void validate(T object);
}
