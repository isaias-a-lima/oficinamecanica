package com.ikservices.oficinamecanica.workorders.application.gateways;

import com.ikservices.oficinamecanica.workorders.domain.WorkOrder;

public interface IWorkOrderBusiness {

    void validate(WorkOrder workOrder);
}
