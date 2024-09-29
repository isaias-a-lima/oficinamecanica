package com.ikservices.oficinamecanica.parts.application.usecases;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.domain.Part;

public class UpdatePart {
	private final PartRepository repository;
	
	public UpdatePart(PartRepository repository) {
		this.repository = repository;
	}
	
	public Part execute(Part part) {
		return repository.updatePart(part);
	}
}
