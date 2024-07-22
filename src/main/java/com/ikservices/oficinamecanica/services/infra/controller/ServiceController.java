package com.ikservices.oficinamecanica.services.infra.controller;

import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Converter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.application.usecases.ListServices;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.services.infra.persistence.ServiceEntity;
import com.sun.xml.bind.v2.TODO;

@RestController
@RequestMapping("/services")
public class ServiceController {
	
	private final ServiceConverter converter;
	private final GetService getService;
	private final ListServices listServices;
	
	public ServiceController(ServiceConverter converter, GetService getService, ListServices listServices) {
		this.getService = getService;
		this.listServices = listServices;
		this.converter = converter;
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> getService(@PathVariable("workshopId") Long workshopId,
			@PathVariable("id") Long id){
		Service service = getService.execute(new ServiceId(id, workshopId));
		return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service)));
	}
	
	@GetMapping("/workshop/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> listService(@PathVariable("id") Long workshopId) {
		List<ServiceDTO> serviceList = null;
		
		/*Add try and catch*/
		serviceList = converter.parseServiceDTOList(listServices.execute(workshopId));
			
		return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(serviceList));
	}
}
