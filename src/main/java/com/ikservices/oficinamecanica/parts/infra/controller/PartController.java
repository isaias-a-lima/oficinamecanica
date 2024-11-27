package com.ikservices.oficinamecanica.parts.infra.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.parts.application.PartBusinessConstant;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.parts.application.usecases.GetNextPartId;
import com.ikservices.oficinamecanica.parts.application.usecases.GetPart;
import com.ikservices.oficinamecanica.parts.application.usecases.GetPartsList;
import com.ikservices.oficinamecanica.parts.application.usecases.SavePart;
import com.ikservices.oficinamecanica.parts.application.usecases.UpdatePart;
import com.ikservices.oficinamecanica.parts.domain.Part;
import com.ikservices.oficinamecanica.parts.domain.PartId;
import com.ikservices.oficinamecanica.parts.infra.PartConverter;
import com.ikservices.oficinamecanica.parts.infra.constants.PartConstant;

@RestController
@RequestMapping("/parts")
public class PartController {
	
	private static final Logger LOGGER = IKLoggerUtil.getLogger(PartController.class);
	@Autowired
	private Environment environment;
	
	private final PartConverter converter;
	private final GetPart getPart;
	private final GetPartsList getPartsList;
	private final SavePart savePart;
	private final GetNextPartId getNextPartId;
	private final UpdatePart updatePart;
	
	public PartController(PartConverter converter, GetPart getPart, 
			GetPartsList getPartsList, SavePart savePart,
			GetNextPartId getNextPartId, UpdatePart updatePart) {
		this.converter = converter;
		this.getPart = getPart;
		this.getPartsList = getPartsList;
		this.savePart = savePart;
		this.getNextPartId = getNextPartId;
		this.updatePart = updatePart;
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<PartDTO>> getPart(@PathVariable("workshopId") 
	Long workshopId, @PathVariable("id") Long id) {
		try {
			Part part = getPart.execute(new PartId(id, workshopId));
			return ResponseEntity.ok(IKResponse.<PartDTO>build().body(new PartDTO(part)));			
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<PartDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<PartDTO>build().addMessage(PartBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(PartConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("/workshop/{id}")
	public ResponseEntity<IKResponse<PartDTO>> getPartList(@PathVariable("id") Long workshopId, @RequestParam(name = "search") String search) {
		try {
			List<PartDTO> partsList = null;
			
			partsList = converter.parseDTOList(getPartsList.execute(workshopId, search));
			
			return ResponseEntity.ok(IKResponse.<PartDTO>build().body(partsList));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<PartDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<PartDTO>build().addMessage(PartBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(PartConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKResponse<PartDTO>> savePart(@RequestBody PartDTO dto, UriComponentsBuilder uriBuilder){
		Part part = null;
		
		try {
			Long nextId = this.getNextPartId.execute(dto.getWorkshopId());
			dto.setPartId(nextId);
			part = savePart.execute(converter.parsePart(dto));		
			URI uri = uriBuilder.path("parts/{workshopId}/{id}").buildAndExpand(part.getPartId().getWorkshopId(), 
					part.getPartId().getId()).toUri();
			return ResponseEntity.created(uri).body(IKResponse.<PartDTO>build().body(
					new PartDTO(part)).addMessage(PartBusinessConstant.SUCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(PartConstant.SAVE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<PartDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<PartDTO>build().addMessage(PartBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(PartConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<PartDTO>> updatePart(@RequestBody PartDTO dto) {
		try {
			Part part = updatePart.execute(converter.parsePart(dto));
			return ResponseEntity.ok(IKResponse.<PartDTO>build().body(
					new PartDTO(part)).addMessage(PartBusinessConstant.SUCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(PartConstant.UPDATE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<PartDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<PartDTO>build().addMessage(PartBusinessConstant.ERROR_CODE, IKMessageType.ERROR, environment.getProperty(PartConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
}
