package com.ikservices.oficinamecanica.workorders.application.usecases;

import com.ikservices.oficinamecanica.workorders.application.gateways.WorkOrderRepository;
import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;

public class UpdatePayments {

    private final WorkOrderRepository repository;

    public UpdatePayments( WorkOrderRepository repository) {
        this.repository = repository;
    }

    public WorkOrder execute(WorkOrder workOrder) {
        return this.repository.updatePayments(workOrder);
    }
}
