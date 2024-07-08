package com.ikservices.oficinamecanica.customers.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepositoryJPA extends JpaRepository<CustomerEntity, CustomerEntityId> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.workshopEntity.id = :workshopId")
    public List<CustomerEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId);
}
