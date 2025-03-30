package com.ikservices.oficinamecanica.payables.infra.gateways;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.payables.application.gateways.PayableRepository;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;
import com.ikservices.oficinamecanica.payables.infra.PayableConverter;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableEntity;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableEntityId;
import com.ikservices.oficinamecanica.payables.infra.persistence.PayableRepositoryJPA;

public class PayableRepositoryImpl implements PayableRepository {
	PayableConverter converter;
	PayableRepositoryJPA repositoryJPA;
	
	public PayableRepositoryImpl(PayableConverter converter, PayableRepositoryJPA repositoryJPA) {
		this.converter = converter;
		this.repositoryJPA = repositoryJPA;
	}
	
	@Override
	public List<Payable> listPayables(Long workshopId) {
		return this.converter.parseEntityToDomainList(repositoryJPA.findAllByWorkshopId(workshopId));
	}
	@Override
	public Payable getPayable(PayableId id) {
		return this.converter.parseEntityToDomain(repositoryJPA.getById(new PayableEntityId(id.getPayableId(),
				id.getWorkshopId())));
	}
	
	@Override
	public Payable savePayable(Payable payable) {
		Optional<PayableEntity> optional = repositoryJPA.
				findById(new PayableEntityId(payable.getId().getPayableId(), payable.getId().getWorkshopId()));
		
		if(optional.isPresent()) {
			throw new IKException("Payable is already present");
		}
		
		PayableEntity savedEntity = repositoryJPA.save(converter.parseDomainToEntity(payable));
		return converter.parseEntityToDomain(savedEntity);
	}
	
	@Override
	public Payable updatePayable(Payable payable) {
		Optional<PayableEntity> optional = repositoryJPA.
				findById(new PayableEntityId(payable.getId().getPayableId(), payable.getId().getWorkshopId()));
		
		PayableEntity entity = optional.orElse(null);
		
		if(Objects.nonNull(entity)) {
			entity.update(converter.parseDomainToEntity(payable));
		}
		
		
		return converter.parseEntityToDomain(entity);
	}

	@Override
	public Integer getNextPayableId(Long workshopId) {
		return repositoryJPA.getNextPayableId(workshopId);
	}
}
