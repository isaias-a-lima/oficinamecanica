package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.customers.application.usecases.GetCustomer;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.application.usecases.SaveCustomer;
import com.ikservices.oficinamecanica.customers.application.usecases.UpdateCustomer;
import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.customers.domain.CustomerId;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final ListCustomers listCustomers;
    private final GetCustomer getCustomer;
    private final CustomerConverter converter;
    private final SaveCustomer saveCustomer;
    private final UpdateCustomer updateCustomer;

    public CustomerController(ListCustomers listCustomers, CustomerConverter converter,
                              GetCustomer getCustomer, SaveCustomer saveCustomer, UpdateCustomer updateCustomer) {
        this.listCustomers = listCustomers;
        this.converter = converter;
        this.getCustomer = getCustomer;
        this.saveCustomer = saveCustomer;
        this.updateCustomer = updateCustomer;
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<IKResponse<CustomerDTO>> listCustomers(@PathVariable("id") Long workshopId) {

        List<CustomerDTO> customerList = null;
        try {
            customerList = converter.parseCustomerResponseList(listCustomers.execute(workshopId));
        } catch (IKException ike) {
            return ResponseEntity.status(ike.getCode()).body(IKResponse.<CustomerDTO>build().addMessage(ike.getIKMessageType(), ike.getMessage()));
        }
        return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(customerList));
    }

    @GetMapping("/{workshopId}/{docId}")
    public ResponseEntity<IKResponse<CustomerDTO>> getCustomer(@PathVariable("workshopId") Long workshopId, @PathVariable("docId") String docId) {
        Customer customer = getCustomer.execute(new CustomerId(workshopId, docId));
        return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customer)));
    }

    @PostMapping
    public ResponseEntity<IKResponse<CustomerDTO>> saveCustomer(@RequestBody CustomerDTO customerDTO, UriComponentsBuilder uriBuilder) {
        Customer customerSaved = saveCustomer.execute(converter.parseCustomer(customerDTO));
        URI uri = uriBuilder.path("/customer/{workshopId}/{docId}").buildAndExpand(customerSaved.getId().getWorkshopId(), customerSaved.getId().getDocId()).toUri();
        return ResponseEntity.created(uri).body(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customerSaved)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<IKResponse<CustomerDTO>> updateCustomer(@RequestBody CustomerDTO dto) {
        Customer customer = updateCustomer.updateCustomer(converter.parseCustomer(dto));
        return ResponseEntity.ok(IKResponse.<CustomerDTO>build().body(new CustomerDTO(customer)));
    }
}
