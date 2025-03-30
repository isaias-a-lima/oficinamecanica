package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrderId;

public class FinalizeWorkOrder {

    private final WorkOrderRepository repository;

    public FinalizeWorkOrder(WorkOrderRepository repository) {
        this.repository = repository;
    }

    public Boolean execute(WorkOrderId id) {
        return this.repository.finalizeWorkOrder(id);
    }
}
