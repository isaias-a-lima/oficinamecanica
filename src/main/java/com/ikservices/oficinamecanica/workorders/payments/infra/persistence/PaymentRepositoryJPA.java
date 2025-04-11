package com.ikservices.oficinamecanica.workorders.payments.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepositoryJPA extends JpaRepository<PaymentEntity, PaymentEntityId> {
}
