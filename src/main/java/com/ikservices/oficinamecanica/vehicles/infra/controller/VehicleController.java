package com.ikservices.oficinamecanica.vehicles.infra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntityId;
import com.ikservices.oficinamecanica.vehicles.application.usecases.DeleteVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.GetVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.ListVehicles;
import com.ikservices.oficinamecanica.vehicles.application.usecases.SaveVehicle;
import com.ikservices.oficinamecanica.vehicles.application.usecases.UpdateVehicle;
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
}
