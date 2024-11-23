package com.ikservices.oficinamecanica.vehicles.infra.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.vehicles.application.usecases.DeleteVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.GetVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.ListVehicles;
import com.ikservices.oficinamecanica.vehicles.application.usecases.SaveVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.UpdateVehicle;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;
import com.ikservices.oficinamecanica.vehicles.infra.constants.VehicleConstant;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("vehicles")
public class VehicleController {
	
    private static final Logger LOGGER = IKLoggerUtil.getLogger(VehicleController.class);
    @Autowired
    private Environment environment;
    
	private final GetVehicle getVehicle;
	private final ListVehicles listVehicles;
	private final SaveVehicle saveVehicle;
	private final DeleteVehicle deleteVehicle;
	private final UpdateVehicle updateVehicle;
	private final VehicleConverter converter;
	
	public VehicleController(GetVehicle getVehicle, ListVehicles listVehicles,
			SaveVehicle saveVehicle, DeleteVehicle deleteVehicle, UpdateVehicle 
			updateVehicle, VehicleConverter converter) {
		this.getVehicle = getVehicle;
		this.listVehicles = listVehicles;
		this.saveVehicle = saveVehicle;
		this.deleteVehicle = deleteVehicle;
		this.updateVehicle = updateVehicle;
		this.converter = converter;
	}
	
	@GetMapping("{workshopId}/{customerId}")
	public ResponseEntity<IKResponse<VehicleResponse>> listVehicles(@PathVariable Long workshopId, 
			@PathVariable String customerId) {
		try {
			List<VehicleResponse> vehicleList = converter.parseResponseList(listVehicles.execute(new IdentificationDocumentVO(customerId), workshopId));
			
			return ResponseEntity.ok(IKResponse.<VehicleResponse>build().body(vehicleList));			
		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<VehicleResponse>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<VehicleResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(VehicleConstant.LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("{vehicleId}")
	public ResponseEntity<IKResponse<VehicleDTO>> getVehicle(@PathVariable Long vehicleId) {
		try {
			Set<Map.Entry<Long, Vehicle>> entries = getVehicle.execute(vehicleId).entrySet();
			VehicleDTO response = null;
			for (Map.Entry<Long, Vehicle> entry : entries) {
				response = new VehicleDTO(entry.getValue(), entry.getKey());
				break;
			}
			if (Objects.isNull(response)) {
				throw new EntityNotFoundException(VehicleConstant.NOT_FOUND_MESSAGE);
			}
			return ResponseEntity.ok(IKResponse.<VehicleDTO>build().body(response));
		} catch (IKException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<VehicleDTO>build().addMessage(e.getIkMessage().getCode(), IKMessageType.getByCode(e.getIkMessage().getType()), e.getIkMessage().getMessage()));
		} catch (EntityNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					IKResponse.<VehicleDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING, environment.getProperty(VehicleConstant.NOT_FOUND_MESSAGE)));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<VehicleDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING, environment.getProperty(VehicleConstant.GET_ERROR_MESSAGE)));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKResponse<VehicleResponse>> saveVehicle(@RequestBody VehicleDTO vehicleDTO, UriComponentsBuilder uriBuilder) {

		try {
			Map<Long, Vehicle> vehicleMap = saveVehicle.execute(converter.parseVehicle(vehicleDTO));
			VehicleResponse response = null;
			ResponseEntity<IKResponse<VehicleResponse>> responseEntity = null;

			for (Long vehicleId : vehicleMap.keySet()) {
				response = new VehicleResponse(vehicleMap.get(vehicleId), vehicleId);

				URI uri = uriBuilder.path("vehicles/{vehicleId}").buildAndExpand(vehicleId).toUri();

				responseEntity = ResponseEntity.created(uri).body(IKResponse.<VehicleResponse>build().body(new VehicleResponse(vehicleMap.get(vehicleId), vehicleId))
						.addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(VehicleConstant.SAVE_SUCCESS_MESSAGE)));
			}

			return responseEntity;

		}catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<VehicleResponse>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<VehicleResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(VehicleConstant.SAVE_ERROR_MESSAGE)));
		}
	}
	
	@Transactional
	@DeleteMapping("{vehicleId}")
	public ResponseEntity<IKResponse<VehicleResponse>> deleteVehicle(@PathVariable Long vehicleId) {
		
		try {
			deleteVehicle.execute(vehicleId);
			return ResponseEntity.status(HttpStatus.OK).body(IKResponse.<VehicleResponse>build().addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(VehicleConstant.DELETE_SUCCESS_MESSAGE)));
		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<VehicleResponse>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<VehicleResponse>build()
					.addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(VehicleConstant.DELETE_ERROR_MESSAGE)));
		}
	}
	
	@Transactional
	@PutMapping()
	public ResponseEntity<IKResponse<VehicleResponse>> updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
		
		try {
			Map<Long, Vehicle> vehicleMap = updateVehicle.execute(vehicleDTO.getVehicleId(), converter.parseVehicle(vehicleDTO));
			return ResponseEntity.ok(IKResponse.<VehicleResponse>build().
					body(new VehicleResponse(vehicleMap.get(vehicleDTO.getVehicleId()), vehicleDTO.getVehicleId()))
					.addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(VehicleConstant.UPDATE_SUCCESS_MESSAGE)));
		} catch(IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<VehicleResponse>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<VehicleResponse>build()
					.addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(VehicleConstant.UPDATE_ERROR_MESSAGE)));
		}
	}
}
