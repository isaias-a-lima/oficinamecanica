package com.ikservices.oficinamecanica.services.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Converter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.services.application.usecases.GetNextServiceId;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.application.usecases.ListServices;
import com.ikservices.oficinamecanica.services.application.usecases.SaveService;
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
	private final SaveService saveService;
	private final GetNextServiceId getNextServiceId;
	
	public ServiceController(ServiceConverter converter, GetService getService, ListServices listServices,
			SaveService saveService, GetNextServiceId getNextServiceId) {
		this.getService = getService;
		this.listServices = listServices;
		this.converter = converter;
		this.saveService = saveService;
		this.getNextServiceId = getNextServiceId;
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
	
	@PostMapping
	public ResponseEntity<IKResponse<ServiceDTO>> saveService(@RequestBody ServiceDTO serviceDTO, UriComponentsBuilder uriBuilder){
		Service savedService = null;
		try {
			Long nextServiceId = this.getNextServiceId.execute(serviceDTO.getWorkshopId());
			serviceDTO.setServiceId(nextServiceId);
			savedService = saveService.execute(converter.parseService(serviceDTO));
		} catch(IKException ike) {
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			return ResponseEntity.status(code).body(IKResponse.<ServiceDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
		}
		URI uri = uriBuilder.path("/services/{workshopId}/{id}").buildAndExpand(savedService.getId().getWorkshopId(),
				savedService.getId().getId()).toUri();
		return ResponseEntity.created(uri).body(IKResponse.<ServiceDTO>build().body(new ServiceDTO(savedService)));		
	}
}
