package com.ikservices.oficinamecanica.services.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.services.application.usecases.GetNextServiceId;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.application.usecases.ListServices;
import com.ikservices.oficinamecanica.services.application.usecases.SaveService;
import com.ikservices.oficinamecanica.services.application.usecases.UpdateService;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;

@RestController
@RequestMapping("/services")
public class ServiceController {
	
	private final ServiceConverter converter;
	private final GetService getService;
	private final ListServices listServices;
	private final SaveService saveService;
	private final GetNextServiceId getNextServiceId;
	private final UpdateService updateService;
	
	public ServiceController(ServiceConverter converter, GetService getService, ListServices listServices,
			SaveService saveService, GetNextServiceId getNextServiceId, UpdateService updateService) {
		this.getService = getService;
		this.listServices = listServices;
		this.converter = converter;
		this.saveService = saveService;
		this.getNextServiceId = getNextServiceId;
		this.updateService = updateService;
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> getService(@PathVariable("workshopId") Long workshopId,
			@PathVariable("id") Long id){
		Service service = getService.execute(new ServiceId(id, workshopId));
		return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service)));
	}
	
	@GetMapping("/workshop/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> listService(@PathVariable("id") Long workshopId, @RequestParam(name = "search") String search) {
		try {
			List<ServiceDTO> serviceList = converter.parseServiceDTOList(listServices.execute(workshopId, search));
			return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(serviceList));
		} catch (IKException ike) {
			return ResponseEntity.status(ike.getCode()).body(IKResponse.<ServiceDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
		}
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
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<ServiceDTO>> updateService(@RequestBody	 ServiceDTO serviceDTO){
		Service service = updateService.execute(converter.parseService(serviceDTO));
		return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service)));
	}
}