package com.ikservices.oficinamecanica.budgets.items.services.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ikservices.oficinamecanica.budgets.infra.persistence.BudgetEntity;

public interface BudgetItemServiceRepositoryJPA extends JpaRepository<BudgetItemServiceEntity, BudgetItemServiceEntityId>{
	
	public List<BudgetEntity> findAllByBudgetEntity(@Param("budgetEntity") BudgetEntity budgetEntity);
	
	@Query("SELECT CASE WHEN MAX(i.id.id) IS NULL THEN 1 ELSE (MAX(i.id.id) + 1) END FROM BudgetItemServiceEntity i WHERE i.budgetEntity.budgetId = :budgetId")
	public Long getNextItemId(@Param("budgetId") Long budgetId);
}
