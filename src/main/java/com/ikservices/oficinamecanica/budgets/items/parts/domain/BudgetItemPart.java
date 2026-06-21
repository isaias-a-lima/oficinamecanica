package com.ikservices.oficinamecanica.budgets.items.parts.domain;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private BigDecimal serviceCost;
    private BigDecimal discountValue;
    private BigDecimal discount;

    public BigDecimal getTotal() {
        return NumberUtil.calcPartPrice(quantity, value, serviceCost, discountValue, discount);
    }

    public BigDecimal getRealDiscount() {
        if (null != this.discount && this.discount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal baseValue = part.getCost().add(this.serviceCost);
            return this.discount.divide(BigDecimal.valueOf(100)).multiply(baseValue).setScale(2, RoundingMode.HALF_UP);
        }
        return this.discountValue.setScale(2, RoundingMode.HALF_UP);
    }

}
