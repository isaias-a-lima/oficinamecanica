package com.ikservices.oficinamecanica.services.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepositoryJPA extends JpaRepository<ServiceEntity, ServiceEntityId>{
	
	public List<ServiceEntity> findAllByWorkshopId(Long id);
}
