package com.ikservices.oficinamecanica.customers.infra.persistence;

import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMERS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    }

}
