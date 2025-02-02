package com.ikservices.oficinamecanica.budgets.items.parts.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BudgetItemPartId implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long itemId;
    private Long budgetId;
}
