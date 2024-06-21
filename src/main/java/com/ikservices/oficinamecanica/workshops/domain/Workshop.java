package com.ikservices.oficinamecanica.workshops.domain;

import com.ikservices.oficinamecanica.users.domain.User;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Workshop {
    private User user;
    private String name;
    private Long cnpj;
    private byte[] image;
}
