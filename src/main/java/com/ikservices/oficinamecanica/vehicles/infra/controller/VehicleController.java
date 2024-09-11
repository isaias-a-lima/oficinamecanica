package com.ikservices.oficinamecanica.vehicles.infra.controller;

import java.awt.TrayIcon.MessageType;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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

@RestController
@RequestMapping("vehicles")
public class VehicleController {
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
		Vehicle vehicle = getVehicle.execute(vehicleId);
		return ResponseEntity.ok(IKResponse.<VehicleResponse>build().body(new VehicleResponse(vehicle)));
	}
	
	@PostMapping()
	public ResponseEntity<IKResponse<VehicleResponse>> saveVehicle(@RequestBody VehicleDTO vehicleDTO, UriComponentsBuilder uriBuilder) {
		Vehicle vehicle = null;
		
		try {
			vehicle = saveVehicle.execute(converter.parseVehicle(vehicleDTO));
			URI uri = uriBuilder.path("vehicles/{workshopId}/{id}").buildAndExpand(vehicle.getCustomer()
					.getWorkshop().getDocId(), vehicle.getCustomer().getId().getDocId().getDocument()).toUri();
			return ResponseEntity.created(uri).body(IKResponse.<VehicleResponse>build().
					body(new VehicleResponse(vehicle)).addMessage(IKMessageType.SUCCESS, "Veículo registrado com sucesso"));
		}catch(IKException ike) {
			int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
			return ResponseEntity.status(code).body(IKResponse.<VehicleResponse>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
		}
	}
}
