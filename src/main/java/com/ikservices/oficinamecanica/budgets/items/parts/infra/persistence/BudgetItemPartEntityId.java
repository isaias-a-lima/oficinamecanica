package com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class BudgetItemPartEntityId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "ITEMID")
    private Long itemId;
    @Column(name = "BUDGETID")
    private Long budgetId;
}
