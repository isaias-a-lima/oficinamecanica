package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public class IdentificationDocumentVO {

    private TaxPayerEnum taxPayerEnum;

    private String document;

    public IdentificationDocumentVO(TaxPayerEnum taxPayerEnum, String document) {
        if (Objects.isNull(taxPayerEnum) || Objects.isNull(document)) {
            throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.ERROR, "Null object.");
        }

        document = document.replaceAll("\\D", "");

        if (TaxPayerEnum.COMPANY_PERSON.equals(taxPayerEnum) && document.length() != 14 ||
                TaxPayerEnum.PHYSICAL_PERSON.equals(taxPayerEnum) && document.length() != 11) {
            throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.ERROR, "Documento inválido.");
        }
        this.taxPayerEnum = taxPayerEnum;
        this.document = document;
    }

    public IdentificationDocumentVO(String document) {
        if (Objects.nonNull(document)) {
            switch (document.length()) {
                case 14:
                    this.taxPayerEnum = TaxPayerEnum.COMPANY_PERSON;
                    this.document = document;
                    break;
                case 11:
                    this.taxPayerEnum = TaxPayerEnum.PHYSICAL_PERSON;
                    this.document = document;
                    break;
                default:
                    throw new IKException(HttpStatus.BAD_REQUEST.value(), IKMessageType.ERROR, "Documento inválido.");
            }
        } else {
            throw new IKException(HttpStatus.INTERNAL_SERVER_ERROR.value(), IKMessageType.ERROR, "Null object.");
        }
    }

    public String getFullDocument() {
        String formattedDocument;

        if (TaxPayerEnum.COMPANY_PERSON.equals(taxPayerEnum)) {
            formattedDocument = document.substring(0, 2) + "." +
                    document.substring(2, 5) + "." +
                    document.substring(5, 8) + "/" +
                    document.substring(8, 12) + "-" +
                    document.substring(12);
        } else {
            formattedDocument = document.substring(0, 3) + "." +
                    document.substring(3, 6) + "." +
                    document.substring(6, 9) + "-" +
                    document.substring(9);
        }

        return formattedDocument;
    }
}
