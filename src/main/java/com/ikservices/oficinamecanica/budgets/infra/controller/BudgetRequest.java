package com.ikservices.oficinamecanica.budgets.infra.controller;

import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.items.services.infra.controller.BudgetItemServiceRequestDTO;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BudgetRequest {
    Long budgetId;
    Long vehicleId;
    String openingDate;
    Long km;
    BudgetStatusEnum budgetStatus;
    String amount;
    List<BudgetItemServiceRequestDTO> serviceItems;
}
