package com.ikservices.oficinamecanica.receivables.infra.persistence;

import com.ikservices.oficinamecanica.workorders.payments.infra.persistence.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReceivableRepositoryJPA extends JpaRepository<ReceivableEntity, ReceivableEntityId> {

    @Query("SELECT CASE WHEN MAX(r.id.orderNumber) IS NULL THEN 1 "
            + "ELSE (MAX(r.id.orderNumber) + 1) END "
            + "FROM ReceivableEntity r WHERE r.id.workshopId = :workshopId")
    public Long getNextId(@Param("workshopId") Long workshopId);

    @Query("SELECT r FROM ReceivableEntity r WHERE "
            + "r.id.workshopId = :workshopId "
            + "AND ((:receivableState = 'PAID' AND r.payDate IS NOT NULL) OR "
            + "(:receivableState = 'UNPAID' AND r.payDate IS NULL) OR "
            + "(:receivableState = 'NONE')) "
            + "AND r.dueDate BETWEEN :startDate AND :endDate "
            + "ORDER BY r.dueDate, r.id.orderNumber")
    public List<ReceivableEntity> listReceivableByDuePeriod(@Param("workshopId") Long workshopId,
                                                       @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                                       @Param("receivableState") String receivableState);
}
