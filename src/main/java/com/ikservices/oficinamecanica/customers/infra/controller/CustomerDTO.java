package com.ikservices.oficinamecanica.customers.infra.controller;

import com.ikservices.oficinamecanica.customers.domain.Customer;
import com.ikservices.oficinamecanica.vehicles.domain.Vehicle;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleDTO;
import com.ikservices.oficinamecanica.vehicles.infra.controller.VehicleResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CustomerDTO implements Serializable {
    private Long workshopId;
    private String docId;
    private String name;
    private String landline;
    private String mobilePhone;
    private String email;
    private String type;
    private String postalCode;
    private String address;
    private String city;
    private String state;
    private List<VehicleResponse> vehicles;

    public CustomerDTO(Customer customer) {
        this.workshopId = customer.getId().getWorkshopId();
        this.docId = customer.getId().getDocId().getFullDocument();
        this.name = customer.getName();
        this.landline = Objects.nonNull(customer.getLandline()) ? customer.getLandline().getFullPhone() : null;
        this.mobilePhone = Objects.nonNull(customer.getMobilePhone()) ? customer.getMobilePhone().getFullPhone() : null;
        this.email = Objects.nonNull(customer.getEmail()) ? customer.getEmail().getMailAddress() : null;
        this.type = Objects.nonNull(customer.getType()) ? customer.getType().getDescription() : null;
        this.address = Objects.nonNull(customer.getAddress()) ? customer.getAddress().getPartialAddress() : null;
        this.city = Objects.nonNull(customer.getAddress()) ? customer.getAddress().getCity() : null;
        this.state = Objects.nonNull(customer.getAddress()) ? customer.getAddress().getState() : null;
        this.postalCode = Objects.nonNull(customer.getAddress()) ? customer.getAddress().getFormattedPostalCode() : null;
        this.vehicles = new ArrayList<>();  	

        if (Objects.nonNull(customer.getVehicles()) && !customer.getVehicles().isEmpty()) {
            for (Map<Long, Vehicle> vehicleMap : customer.getVehicles()) {
                for (Map.Entry<Long, Vehicle> vehicleEntry : vehicleMap.entrySet()) {
                    this.vehicles.add(new VehicleResponse(vehicleEntry.getValue(), vehicleEntry.getKey()));
                }
            }
        }
    }
}
