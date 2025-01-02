package com.ikservices.oficinamecanica.budgets.infra;

import com.ikservices.oficinamecanica.budgets.domain.Budget;
import com.ikservices.oficinamecanica.budgets.domain.BudgetStatusEnum;
import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BudgetConverterTest {

    private BudgetConverter subject;

    @BeforeEach
    public void setup() {
        VehicleConverter vehicleConverter = new VehicleConverter();
        subject = new BudgetConverter(vehicleConverter);
    }

    @Test
    public void testParseBudgetList() {
        int qtd = 3;
        List<Map<Long, Map<Long, Budget>>> longMapMap = subject.parseBudgetList(getBudgetEntityList(qtd));
        Assertions.assertNotNull(longMapMap);
    }

    private List<BudgetEntity> getBudgetEntityList(int itemQtd) {
        List<BudgetEntity> entities = new ArrayList<>();
        for (int i=1; i<=itemQtd; i++) {
            entities.add(getBudgetEntity(i));
        }
        return entities;
    }

    private BudgetEntity getBudgetEntity(int index) {
        BudgetEntity entity = new BudgetEntity();
        entity.setBudgetId(Long.parseLong(String.valueOf(index)));
        entity.setVehicleId(Long.parseLong(String.valueOf(index)));
        entity.setOpeningDate(LocalDate.now());
        entity.setKm(Long.parseLong(String.valueOf(100*index)));
        entity.setBudgetStatus(BudgetStatusEnum.ACTIVE);
        entity.setAmount(BigDecimal.ZERO);
        return entity;
    }
}
