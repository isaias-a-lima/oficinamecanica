package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKRes;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import com.ikservices.oficinamecanica.commons.vo.IdentificationDocumentVO;
import com.ikservices.oficinamecanica.customers.application.usecases.*;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(CustomerController.class);

    private final ListCustomers listCustomers;
    private final GetCustomer getCustomer;
    private final CustomerConverter converter;
    private final SaveCustomer saveCustomer;
    private final UpdateCustomer updateCustomer;
    private final GetCustomerByVeclicle getCustomerByVeclicle;

    public CustomerController(ListCustomers listCustomers, CustomerConverter converter,
                              GetCustomer getCustomer, SaveCustomer saveCustomer, UpdateCustomer updateCustomer, GetCustomerByVeclicle getCustomerByVeclicle) {
        this.listCustomers = listCustomers;
        this.converter = converter;
        this.getCustomer = getCustomer;
        this.saveCustomer = saveCustomer;
        this.updateCustomer = updateCustomer;
        this.getCustomerByVeclicle = getCustomerByVeclicle;
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<IKResponse<CustomerDTO>> listCustomers(@PathVariable("id") Long workshopId, @RequestParam(name = "criteria") int criteria, @RequestParam(name = "search") String search) {

        List<CustomerDTO> customerList = null;
        try {
            customerList = converter.parseCustomerResponseList(listCustomers.execute(workshopId, criteria, search));
        } catch (IKException ike) {
            return ResponseEntity.status(ike.getCode()).body(IKResponse.<CustomerDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
        }
        return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(customerList));
    }

    @GetMapping("/{workshopId}/{docId}")
    public ResponseEntity<IKResponse<CustomerDTO>> getCustomer(@PathVariable("workshopId") Long workshopId, @PathVariable("docId") String docId, @RequestParam(name = "type") Character type) {
        if (Objects.isNull(type)) {
            type = docId.length() == 14 ? TaxPayerEnum.COMPANY_PERSON.getType() : TaxPayerEnum.PHYSICAL_PERSON.getType();
        }
        Customer customer = getCustomer.execute(new CustomerId(workshopId, new IdentificationDocumentVO(TaxPayerEnum.getByType(type), docId)));
        return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customer)));
    }

    @PostMapping
    public ResponseEntity<IKResponse<CustomerDTO>> saveCustomer(@RequestBody CustomerDTO customerDTO, UriComponentsBuilder uriBuilder) {
        String logID = IKLoggerUtil.getLoggerID();
        try {
            Customer customerSaved = saveCustomer.execute(converter.parseCustomer(customerDTO));
            URI uri = uriBuilder.path("/customer/{workshopId}/{docId}").buildAndExpand(customerSaved.getId().getWorkshopId(), customerSaved.getId().getDocId().getDocument()).toUri();
            return ResponseEntity.created(uri).body(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customerSaved)).addMessage(IKMessageType.SUCCESS, "Cadastro realizado com sucesso."));
        } catch (IKException ike) {
            LOGGER.error(logID + " saveCustomer: " + IKLoggerUtil.parseJSON(customerDTO), ike);
            int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
            return ResponseEntity.status(code).body(IKResponse.<CustomerDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
        } catch (Exception e) {
            LOGGER.error(logID + " saveCustomer: " + IKLoggerUtil.parseJSON(customerDTO), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<CustomerDTO>build().addMessage(IKMessageType.ERROR, "ERRO inesperado. Se persistir informe ao suporte o seguinte ID: " + logID));
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<IKResponse<CustomerDTO>> updateCustomer(@RequestBody CustomerDTO dto) {
        String logID = IKLoggerUtil.getLoggerID();
        try {
            Customer customer = updateCustomer.updateCustomer(converter.parseCustomer(dto));
            return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customer)).addMessage(IKMessageType.SUCCESS, "Cliente alterado com sucesso."));
        } catch (IKException ike) {
            LOGGER.error(logID + " saveCustomer: " + IKLoggerUtil.parseJSON(dto), ike);
            int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
            return ResponseEntity.status(code).body(IKResponse.<CustomerDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
        } catch (Exception e) {
            LOGGER.error(logID + " saveCustomer: " + IKLoggerUtil.parseJSON(dto), e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<CustomerDTO>build().addMessage(IKMessageType.ERROR, "ERRO inesperado. Se persistir informe ao suporte o seguinte ID: " + logID));
        }
    }

    @GetMapping("workshop/vehicle/{workshopId}/{plate}")
    public ResponseEntity<IKRes<CustomerDTO>> getCustomerByVehicle(@PathVariable("workshopId") Long workshopId, @PathVariable("plate") String plate) {
        String logID = IKLoggerUtil.getLoggerID();
        try {
            List<Customer> customers = getCustomerByVeclicle.execute(workshopId, plate);
            return ResponseEntity.ok(IKRes.<CustomerDTO>build().body(converter.parseCustomerResponseList(customers)));
        } catch (IKException ike) {
            String logMsg = String.format("ID: %s - getCustomerByVehicle - workshopId: %d, plate: %s", logID, workshopId, plate);
            LOGGER.error(logMsg, ike.getCause());
            int code = Objects.nonNull(ike.getCode()) ? ike.getCode() : 500;
            return ResponseEntity.status(code).body(IKRes.<CustomerDTO>build().addMessage(ike.getMessage()));
        } catch (Exception e) {
            String logMsg = String.format("ID: %s - getCustomerByVehicle - workshopId: %d, plate: %s", logID, workshopId, plate);
            LOGGER.error(logMsg, e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKRes.<CustomerDTO>build().addMessage("ERRO inesperado. Se persistir informe ao suporte o seguinte ID: " + logID));
        }
    }
}
