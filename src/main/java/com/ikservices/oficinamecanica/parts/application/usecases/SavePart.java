package com.ikservices.oficinamecanica.parts.application.usecases;

import com.ikservices.oficinamecanica.parts.application.gateways.PartRepository;
import com.ikservices.oficinamecanica.parts.domain.Part;

public class SavePart {
	private final PartRepository repository;
	
	public SavePart(PartRepository repository) {
		this.repository = repository;
	}
	
	public Part execute(Part part) {
		return repository.savePart(part);
	}
}
