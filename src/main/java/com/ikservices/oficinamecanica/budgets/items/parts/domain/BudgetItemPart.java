package com.ikservices.oficinamecanica.budgets.items.parts.domain;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BudgetItemPart {
    private BudgetItemPartId id;
    private Part part;
    private Integer quantity;
    private BigDecimal value;
    private BigDecimal discount;

    public BigDecimal getTotal() {
        return NumberUtil.calcPrice(quantity, part.getCost(), discount);
    }

}
