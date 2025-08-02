package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessage;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class CPFVO {

    private static final String EMPTY_PARAM_MESSAGE = "É necessário informar o CPF.";
    private static final String INVALID_PARAM_MESSAGE = "O CPF %s não é válido.";
    private static final int CPF_LENGTH = 11;
    private final Long cpf;

    public CPFVO(String cpf) {
        this.cpf = this.validations(cpf);
    }

    public CPFVO(Long cpf) {
        this.cpf = this.validations(cpf);
    }

    public Long getCpf() {
        return this.cpf;
    }

    public String getTextCpf() {
        return parseCPFNumberToText(getCpf());
    }

    /**
     * It checks if the CPF is valid.
     * @param cpf
     * @return
     */
    public static boolean isCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) return false;

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Calcula primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;

        // Calcula segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        // Verifica se os dígitos calculados são iguais aos do CPF informado
        return cpf.charAt(9) - '0' == primeiroDigito && cpf.charAt(10) - '0' == segundoDigito;
    }

    private String parseCPFNumberToText(Long number) {
        String str = "%0" + CPF_LENGTH + "d";
        return String.format(str, number);
    }

    private Long validations(String cpf) {
        if (Objects.isNull(cpf)) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_BAD_REQUEST_CODE, IKMessageType.WARNING.getCode(), EMPTY_PARAM_MESSAGE));
        }

        String str = String.format(INVALID_PARAM_MESSAGE, cpf);

        cpf = cpf.replaceAll("\\D", "");

        if (!isCPF(cpf)) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_BAD_REQUEST_CODE, IKMessageType.WARNING.getCode(), str));
        }

        return Long.parseLong(cpf.replaceAll("\\D", ""));
    }

    private Long validations(Long cpf) {
        if (Objects.isNull(cpf)) {
            throw new IKException(new IKMessage(Constants.IK_HTTP_BAD_REQUEST_CODE, IKMessageType.WARNING.getCode(), EMPTY_PARAM_MESSAGE));
        }
        String strCPF = parseCPFNumberToText(cpf);
        return this.validations(strCPF);
    }
}
