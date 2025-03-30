package com.ikservices.oficinamecanica.workorders.items.parts.domain;

import com.ikservices.oficinamecanica.commons.utils.NumberUtil;
import com.ikservices.oficinamecanica.parts.domain.Part;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WorkOrderPartItem {
    private WorkOrderPartItemId id;
    private Part part;
    private Integer quantity;
    private BigDecimal itemValue;
    private BigDecimal discount;

    public BigDecimal getTotal() {
        return NumberUtil.calcPrice(this.quantity, this.itemValue, this.discount);
    }
}
