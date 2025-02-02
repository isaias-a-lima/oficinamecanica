package com.ikservices.oficinamecanica.budgets.items.parts.infra.persistence;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;
import com.ikservices.oficinamecanica.budgets.items.services.infra.persistence.BudgetItemServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetItemPartRepositoryJPA extends JpaRepository<BudgetItemPartEntity, BudgetItemPartEntityId> {

    @Query("SELECT i FROM BudgetItemPartEntity i WHERE i.id.budgetId = :budgetId")
    public List<BudgetItemPartEntity> findAllByBudget(@Param("budgetId") Long budgetId);

    @Query("SELECT CASE WHEN MAX(i.id.itemId) IS NULL THEN 1 ELSE (MAX(i.id.itemId) + 1) END FROM BudgetItemPartEntity i WHERE i.id.budgetId = :budgetId")
    public Long getNextItemId(@Param("budgetId") Long budgetId);
}
