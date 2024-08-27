package com.ikservices.oficinamecanica.customers.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepositoryJPA extends JpaRepository<CustomerEntity, CustomerEntityId> {

    @Query(
            "SELECT c FROM CustomerEntity c WHERE c.id.workshopId = :workshopId " +
                    "AND ((1 = :criteria AND c.id.docId = :search) " +
                    "OR (2 = :criteria AND c.name LIKE concat('%', :search, '%'))" +
                    "OR (3 = :criteria AND c.mobilePhone LIKE concat('%', :search, '%')))"
    )
    public List<CustomerEntity> findAllByWorkshopId(@Param("workshopId") Long workshopId, @Param("criteria") int criteria, @Param("search") String search);
}
