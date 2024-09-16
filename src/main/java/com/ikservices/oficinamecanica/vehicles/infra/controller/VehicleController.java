package com.ikservices.oficinamecanica.vehicles.infra.controller;

import java.awt.TrayIcon.MessageType;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ikservices.oficinamecanica.commons.response.IKRes;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.suppliers.infra.controller.SupplierDTO;
import com.ikservices.oficinamecanica.vehicles.application.usecases.DeleteVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.GetVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.ListVehicles;
import com.ikservices.oficinamecanica.vehicles.application.usecases.SaveVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.UpdateVehicle;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.VehicleConverter;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(VehicleController.class);

	private static final String DEFAULT_SERVER_ERROR_MESSAGE = "Ocorreu um imprevisto, favor tentar novamente mais tarde.";
	private GetVehicle getVehicle;
	private ListVehicles listVehicles;
	private SaveVehicle saveVehicle;
	private DeleteVehicle deleteVehicle;
	private UpdateVehicle updateVehicle;
	private VehicleConverter converter;
	
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
		List<VehicleResponse> vehicleList = converter.
				parseResponseList(listVehicles.execute(new IdentificationDocumentVO(customerId), workshopId));
		
		return ResponseEntity.ok(IKResponse.<VehicleResponse>build().body(vehicleList));
	}
	
	@GetMapping("{vehicleId}")
	public ResponseEntity<IKResponse<VehicleResponse>> getVehicle(@PathVariable Long vehicleId) {
		try {
			Set<Map.Entry<Long, Vehicle>> entries = getVehicle.execute(vehicleId).entrySet();
			VehicleResponse response = null;
			for (Map.Entry<Long, Vehicle> entry : entries) {
				response = new VehicleResponse(entry.getValue(), entry.getKey());
				break;
			}
			if (Objects.isNull(response)) {
				throw new IKException(HttpStatus.BAD_GATEWAY.value(), IKMessageType.WARNING, DEFAULT_SERVER_ERROR_MESSAGE);
			}
			return ResponseEntity.ok(IKResponse.<VehicleResponse>build().body(response));
		} catch (IKException e) {
			return ResponseEntity.status(e.getCode()).body(IKResponse.<VehicleResponse>build().addMessage(e.getIKMessageType(), e.getMessage()));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(IKResponse.<VehicleResponse>build().addMessage(IKMessageType.WARNING, "Veículo não encontrado"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<VehicleResponse>build().addMessage(IKMessageType.WARNING, DEFAULT_SERVER_ERROR_MESSAGE));
		}
	}
	
	@PostMapping()
	public ResponseEntity<IKRes<VehicleResponse>> saveVehicle(@RequestBody VehicleDTO vehicleDTO, UriComponentsBuilder uriBuilder) {

		try {
			Map<Long, Vehicle> vehicleMap = saveVehicle.execute(converter.parseVehicle(vehicleDTO));
			VehicleResponse response = null;
			ResponseEntity<IKRes<VehicleResponse>> responseEntity = null;

			for (Long vehicleId : vehicleMap.keySet()) {
				response = new VehicleResponse(vehicleMap.get(vehicleId), vehicleId);

				URI uri = uriBuilder.path("vehicles/{vehicleId}").buildAndExpand(vehicleId).toUri();

				responseEntity = ResponseEntity.created(uri).body(IKRes.<VehicleResponse>build().
						body(new VehicleResponse(vehicleMap.get(vehicleId), vehicleId)).addMessage("Veículo registrado com sucesso"));
			}

			return responseEntity;

		}catch(IKException ike) {
			LOGGER.error("Erro ao salvar", ike);
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			return ResponseEntity.status(code).body(IKRes.<VehicleResponse>build().addMessage(ike.getMessage()));
		} catch (Exception e) {
            LOGGER.error("Erro ao salvar", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKRes.<VehicleResponse>build().addMessage(DEFAULT_SERVER_ERROR_MESSAGE));
		}
	}
	
	@Transactional
	@DeleteMapping("{vehicleId}")
	public ResponseEntity<IKRes<VehicleResponse>> deleteVehicle(@PathVariable
			Long vehicleId) {
		
		try {
			deleteVehicle.execute(vehicleId);
			return ResponseEntity.status(HttpStatus.OK).
					body(IKRes.<VehicleResponse>build().addMessage("Veículo removido."));
		} catch(IKException ike) {
			LOGGER.error("Erro ao deletar", ike);
			return ResponseEntity.status(ike.getCode()).
					body(IKRes.<VehicleResponse>build().addMessage(ike.getMessage()));
		} catch(Exception e) {
			LOGGER.error("Erro ao deletar", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKRes.<VehicleResponse>build().addMessage(DEFAULT_SERVER_ERROR_MESSAGE));
		}
	}
}
