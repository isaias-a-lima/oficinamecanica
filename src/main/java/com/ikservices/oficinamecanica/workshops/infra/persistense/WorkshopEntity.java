package com.ikservices.oficinamecanica.workshops.infra.persistense;

import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
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

    @OneToOne(optional = false)
    @JoinColumn(name = "CPF")
    private UserEntity user;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DOCID")
    private Long docId;

    @Column(name = "IMAGE")
    private byte[] image;

    @Column(name = "ACTIVE")
    private Boolean active;

    public void update(WorkshopEntity entity) {
        if (Objects.nonNull(entity.getUser())) {
            this.user = entity.getUser();
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
