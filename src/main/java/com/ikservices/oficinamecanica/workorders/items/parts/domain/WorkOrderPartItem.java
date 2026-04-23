package com.ikservices.oficinamecanica.workorders.items.parts.domain;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WorkOrderPartItem {
    private WorkOrderPartItemId id;
    private Part part;
    private Integer quantity;
    private BigDecimal itemValue;
    private BigDecimal serviceCost;
    private BigDecimal discountValue;
    private BigDecimal discount;

    public BigDecimal getTotal() {
        return NumberUtil.calcPartPrice(this.quantity, this.itemValue, this.serviceCost, this.discountValue, this.discount);
    }

    public BigDecimal getRealDiscount() {
        if (null != this.discount && this.discount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal baseValue = part.getCost().add(this.serviceCost);
            return this.discount.divide(BigDecimal.valueOf(100)).multiply(baseValue).setScale(2, RoundingMode.HALF_UP);
        }
        return this.discountValue.setScale(2, RoundingMode.HALF_UP);
    }
}
