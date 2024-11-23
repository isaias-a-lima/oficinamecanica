package com.ikservices.oficinamecanica.services.infra.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.services.application.usecases.GetNextServiceId;
import com.ikservices.oficinamecanica.services.application.usecases.GetService;
import com.ikservices.oficinamecanica.services.application.usecases.ListServices;
import com.ikservices.oficinamecanica.services.application.usecases.SaveService;
import com.ikservices.oficinamecanica.services.application.usecases.UpdateService;
import com.ikservices.oficinamecanica.services.domain.Service;
import com.ikservices.oficinamecanica.services.domain.ServiceId;
import com.ikservices.oficinamecanica.services.infra.ServiceConverter;
import com.ikservices.oficinamecanica.services.infra.constants.ServiceConstant;

@RestController
@RequestMapping("/services")
public class ServiceController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(ServiceController.class);
	@Autowired
	private Environment environment;
	
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
		try {
			Service service = getService.execute(new ServiceId(id, workshopId));
			return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service)));	
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<ServiceDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<ServiceDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(ServiceConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("/workshop/{id}")
	public ResponseEntity<IKResponse<ServiceDTO>> listService(@PathVariable("id") Long workshopId, @RequestParam(name = "search") String search) {
		try {
			List<ServiceDTO> serviceList = converter.parseServiceDTOList(listServices.execute(workshopId, search));
			return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(serviceList));
		} catch (IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<ServiceDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<ServiceDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(ServiceConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping
	public ResponseEntity<IKResponse<ServiceDTO>> saveService(@RequestBody ServiceDTO serviceDTO, UriComponentsBuilder uriBuilder){
		Service savedService = null;
		try {
			Long nextServiceId = this.getNextServiceId.execute(serviceDTO.getWorkshopId());
			serviceDTO.setServiceId(nextServiceId);
			savedService = saveService.execute(converter.parseService(serviceDTO));
			
			URI uri = uriBuilder.path("/services/{workshopId}/{id}").buildAndExpand(savedService.getId().getWorkshopId(),
					savedService.getId().getId()).toUri();
			return ResponseEntity.created(uri).body(IKResponse.<ServiceDTO>build().body(new ServiceDTO(savedService))
					.addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(ServiceConstant.SAVE_SUCCESS_MESSAGE)));
		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<ServiceDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<ServiceDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(ServiceConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<ServiceDTO>> updateService(@RequestBody ServiceDTO serviceDTO){
		try {
			Service service = updateService.execute(converter.parseService(serviceDTO));
			return ResponseEntity.ok(IKResponse.<ServiceDTO>build().body(new ServiceDTO(service))
					.addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(ServiceConstant.UPDATE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<ServiceDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<ServiceDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(ServiceConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
}