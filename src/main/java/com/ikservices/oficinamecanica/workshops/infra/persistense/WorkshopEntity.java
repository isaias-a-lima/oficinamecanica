package com.ikservices.oficinamecanica.workshops.infra.persistense;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "WORKSHOPS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkshopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKSHOPID")
    private Long id;

    @Column(name = "CPF")
    private Long cpf;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DOCID")
    private Long docId;

    @Column(name = "IMAGE")
    private byte[] image;

    @Column(name = "ACTIVE")
    private Boolean active;

    public void update(WorkshopEntity entity) {
        if (Objects.nonNull(entity.getCpf())) {
            this.cpf = entity.getCpf();
        }
        if (Objects.nonNull(entity.getName())) {
            this.name = entity.getName();
        }
        if (Objects.nonNull(entity.getDocId())) {
            this.docId = entity.getDocId();
        }
        if (Objects.nonNull(entity.getImage())) {
            this.image = entity.getImage();
        }
    }

    public void delete() {
        this.active = false;
    }

    public void restore() {
        this.active = true;
    }

}
