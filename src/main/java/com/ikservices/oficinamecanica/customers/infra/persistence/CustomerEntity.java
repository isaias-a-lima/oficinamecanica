package com.ikservices.oficinamecanica.customers.infra.persistence;

import com.ikservices.oficinamecanica.vehicles.infra.persistence.VehicleEntity;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMERS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CustomerEntity {
    @EmbeddedId
    private CustomerEntityId id;
    @ManyToOne
    @JoinColumn(name = "WORKSHOPID", insertable = false , updatable = false)
    private WorkshopEntity workshopEntity;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LANDLINE")
    private String landline;
    @Column(name = "MOBILEPHONE")
    private String mobilePhone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TYPE")
    private Character type;
    @Column(name = "POSTALCODE")
    private String postalCode;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @OneToMany(mappedBy = "customerEntity")
    private List<VehicleEntity> vehicles;

    public void update(CustomerEntity customerEntity) {
        if (Objects.nonNull(customerEntity.getId().getDocId())) {
            this.getId().setDocId(customerEntity.getId().getDocId());
        }
        if (Objects.nonNull(customerEntity.getName())) {
            this.name = customerEntity.getName();
        }
        if (Objects.nonNull(customerEntity.getLandline())) {
            this.landline = customerEntity.getLandline();
        }
        if (Objects.nonNull(customerEntity.getMobilePhone())) {
            this.mobilePhone = customerEntity.getMobilePhone();
        }
        if (Objects.nonNull(customerEntity.getEmail())) {
            this.email = customerEntity.getEmail();
        }
        if (Objects.nonNull(customerEntity.getType())) {
            this.type = customerEntity.getType();
        }
        if (Objects.nonNull(customerEntity.getPostalCode())) {
            this.postalCode = customerEntity.getPostalCode();
        }
        if (Objects.nonNull(customerEntity.getAddress())) {
            this.address = customerEntity.getAddress();
        }
        if (Objects.nonNull(customerEntity.getCity())) {
            this.city = customerEntity.getCity();
        }
        if (Objects.nonNull(customerEntity.getState())) {
            this.state = customerEntity.getState();
        }
    }

}
