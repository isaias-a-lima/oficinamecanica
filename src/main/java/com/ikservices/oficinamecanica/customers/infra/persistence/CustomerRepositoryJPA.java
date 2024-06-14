package com.ikservices.oficinamecanica.customers.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepositoryJPA extends JpaRepository<CustomerEntity, Long> {

    public List<CustomerEntity> findAllByWorkshopId(Long id);
}
