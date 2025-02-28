package com.ikservices.oficinamecanica.workorders.application.gateways;

public interface IWorkOrderBusiness<T> {

    void validate(T object);
}
