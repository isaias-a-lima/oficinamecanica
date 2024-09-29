package com.ikservices.oficinamecanica.parts.application.usecases;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;

public class GetPart {
	private final PartRepository repository;
	
	public GetPart(PartRepository repository) {
		this.repository = repository;
	}
	
	public Part execute(PartId id) {
		return this.repository.getPart(id);
	}
}
