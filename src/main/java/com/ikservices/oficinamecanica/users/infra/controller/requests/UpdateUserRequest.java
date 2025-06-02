package com.ikservices.oficinamecanica.users.infra.controller.requests;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.constants.UserConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class UpdateUserRequest {
    @NotNull(message = "CPF não deve ser nulo")
    private Long cpf;

    @NotNull(message = "Nome não deve ser nulo")
    @NotBlank(message = "Nome não deve estar em branco")
    @Size(min = 2, max = 100, message = "Nome deve ter de 2 a 100 caracteres")
    @Pattern(regexp = "[a-zA-ZáàãâçéêíôõúüÃÂÁÀÉÊÕÔÚÜ ]+", message = "Nome deve conter apenas caracteres latinos comuns")
    private String name;

    @NotNull(message = "E-mail não deve ser nulo")
    @NotBlank(message = "E-mail não deve estar em branco")
    @Size(min = 2, max = 100, message = "E-mail deve ter de 2 a 100 caracteres")
    @Pattern(regexp = IKConstants.EMAIL_PATTERN, message = "E-mail inválido.")
    private String email;

    @NotNull(message = "Os dados antigos não podem ser nulos")
    private UserResponse oldUser;

    public User getNewUser() {
        return new User(cpf, name, email, "", true);
    }

    public User getOldUser() {
        return new User(oldUser.getCpf(), oldUser.getName(), oldUser.getEmail(), null, oldUser.isActive());
    }
}
