package com.ikservices.oficinamecanica.suppliers.infra.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.suppliers.infra.constants.SupplierConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetNextSupplierId;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.GetSupplierList;
import com.ikservices.oficinamecanica.suppliers.application.usecases.SaveSupplier;
import com.ikservices.oficinamecanica.suppliers.application.usecases.UpdateSupplier;
import com.ikservices.oficinamecanica.suppliers.domain.Supplier;
import com.ikservices.oficinamecanica.suppliers.domain.SupplierId;
import com.ikservices.oficinamecanica.suppliers.infra.SupplierConverter;

@RestController
@RequestMapping("suppliers")
public class SupplierController {

	private static final Logger LOGGER = IKLoggerUtil.getLogger(SupplierController.class);
	@Autowired
	private Environment environment;
	private final GetSupplierList getSupplierList;
	private final SupplierConverter converter;
	private final GetSupplier getSupplier;
	private final UpdateSupplier updateSupplier;
	private final SaveSupplier saveSupplier;
	private final GetNextSupplierId getNextSupplierId;
	
	public SupplierController(GetSupplierList getSupplierList, 
			SupplierConverter converter, GetSupplier getSupplier, UpdateSupplier updateSupplier,
			SaveSupplier saveSupplier, GetNextSupplierId getNextSupplierId) {
		this.getSupplierList = getSupplierList;
		this.converter = converter;
		this.getSupplier = getSupplier;
		this.saveSupplier = saveSupplier;
		this.updateSupplier = updateSupplier;
		this.getNextSupplierId = getNextSupplierId;
	}
	
	@GetMapping("/{workshopId}")
	public ResponseEntity<IKResponse<SupplierDTO>> getSupplierList(@PathVariable("workshopId") Long workshopId, @RequestParam(name = "search") String search) {
		try {
			List<SupplierDTO> supplierList = converter.parseDTOList(getSupplierList.execute(workshopId, search));

			return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(supplierList));

		} catch (IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<SupplierDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<SupplierDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(SupplierConstants.SUPPLIER_LIST_ERROR_MESSAGE)));
		}
	}
	
	@GetMapping("/{workshopId}/{id}")
	public ResponseEntity<IKResponse<SupplierDTO>> getSupplier(@PathVariable Long workshopId, @PathVariable Long id) {
		try {
			Supplier supplier = getSupplier.execute(new SupplierId(id, workshopId));
			return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier)));
		} catch (IKException ike) {
			LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
					IKResponse.<SupplierDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<SupplierDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(SupplierConstants.SUPPLIER_GET_ERROR_MESSAGE))
			);
		}
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<IKResponse<SupplierDTO>> updateSupplier(@RequestBody SupplierDTO dto) {
        try {
            Supplier supplier = updateSupplier.execute(converter.parseSupplier(dto));
            return ResponseEntity.ok(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier))
					.addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(SupplierConstants.SUPPLIER_UPDATE_SUCCESS_MESSAGE)));
        } catch (IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    IKResponse.<SupplierDTO>build().addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<SupplierDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(SupplierConstants.SUPPLIER_UPDATE_ERROR_MESSAGE)));
        }
    }
	
	@PostMapping
	public ResponseEntity<IKResponse<SupplierDTO>> saveSupplier(@RequestBody SupplierDTO dto, UriComponentsBuilder uriBuilder) {
		Supplier supplier = null;
		
		try {
			Long nextId = getNextSupplierId.execute(dto.getWorkshopId());
			dto.setSupplierId(nextId);
			supplier = saveSupplier.execute(converter.parseSupplier(dto));
			URI uri = uriBuilder.path("suppliers/{workshopId}/{id}").
					buildAndExpand(supplier.getSupplierId().getWorkshopid(),
							supplier.getSupplierId().getId()).toUri();
			return ResponseEntity.created(uri).body(IKResponse.<SupplierDTO>build().body(converter.parseDTO(supplier))
                    .addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(SupplierConstants.SUPPLIER_SAVE_SUCCESS_MESSAGE)));
		}catch(IKException ike) {
            LOGGER.error(ike.getMessage(), ike);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(IKResponse.<SupplierDTO>build()
					.addMessage(ike.getIkMessage().getCode(), IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
		} catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					IKResponse.<SupplierDTO>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(SupplierConstants.SUPPLIER_SAVE_ERROR_MESSAGE)));
        }
	}
}
