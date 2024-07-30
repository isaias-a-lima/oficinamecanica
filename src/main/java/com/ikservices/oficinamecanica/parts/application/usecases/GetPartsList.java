package com.ikservices.oficinamecanica.parts.application.usecases;

import java.util.List;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.domain.Part;

public class GetPartsList {
	private final PartRepository repository;
	
	public GetPartsList(PartRepository repository) {
		this.repository = repository;
	}
	
	public List<Part> execute(Long workshopId) {
		return this.repository.getPartsList(workshopId);
	}
}
