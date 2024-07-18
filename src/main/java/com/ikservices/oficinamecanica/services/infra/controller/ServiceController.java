package com.ikservices.oficinamecanica.services.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;

@RestController
@RequestMapping("/services")
public class ServiceController {
	
	private final GetService getService;
	
	public ServiceController(GetService getService) {
		this.getService = getService;
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> getService(@PathVariable("workshopId") Long workshopId,
			@PathVariable("id") Long id){
		Service service = getService.execute(new ServiceId(id, workshopId));
		return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service)));
	}
}
