package com.ikservices.oficinamecanica.customers.infra.persistence;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMERID")
    private Long id;
    @Column(name = "WORKSHOPID")
    private Long workshopId;
    @Column(name = "DOCID")
    private Long docId;
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
        if (Objects.nonNull(customerEntity.getDocId())) {
            this.docId = customerEntity.getDocId();
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
