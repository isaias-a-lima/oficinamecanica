package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.enumerates.TaxPayerEnum;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IdentificationDocumentVOTest {

    private IdentificationDocumentVO subject;

    @Test
    public void testFormatCNPJDocument() {
        String cnpj = "12345678000199";
        String formattedCnpj = "12.345.678/0001-99";

        subject = new IdentificationDocumentVO(TaxPayerEnum.COMPANY_PERSON, cnpj);

        Assertions.assertEquals(formattedCnpj, subject.getFullDocument());

    }

    @Test
    public void testFormatCPFDocument() {
        String cpf = "12345678912";
        String formattedCpf = "123.456.789-12";

        subject = new IdentificationDocumentVO(TaxPayerEnum.PHYSICAL_PERSON, cpf);

        Assertions.assertEquals(formattedCpf, subject.getFullDocument());
    }

    @Test
    public void testDocumentWithNonNumericCharacters() {
        String cnpj = "12.345.678/0001-99XCVF";
        String formattedCnpj = "12.345.678/0001-99";

        subject = new IdentificationDocumentVO(TaxPayerEnum.COMPANY_PERSON, cnpj);

        Assertions.assertEquals(formattedCnpj, subject.getFullDocument());

    }

    @Test
    public void testDocumentWithMoreDigitsThanExpected() {
        String cnpj = "123456780001987765";
        String formattedCnpj = "12.345.678/0001-99";

        Assertions.assertThrows(IKException.class, ()-> new IdentificationDocumentVO(TaxPayerEnum.COMPANY_PERSON, cnpj));

    }

    @Test
    public void testCNPJDocumentWithOneParamInConstructor() {
        String cnpj = "12345678000199";
        String formattedCnpj = "12.345.678/0001-99";

        subject = new IdentificationDocumentVO(cnpj);

        Assertions.assertEquals(formattedCnpj, subject.getFullDocument());
    }

    @Test
    public void testCPFDocumentWithOneParamInConstructor() {
        String cpf = "12345678912";
        String formattedCpf = "123.456.789-12";

        subject = new IdentificationDocumentVO(cpf);

        Assertions.assertEquals(formattedCpf, subject.getFullDocument());
    }

}
