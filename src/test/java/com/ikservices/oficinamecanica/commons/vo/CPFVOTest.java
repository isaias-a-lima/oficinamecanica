package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class CPFVOTest {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(CPFVOTest.class);
    private CPFVO subject;

    @Test
    public void testNullParam() {
        String str = null;
        try {
            subject = new CPFVO(str);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(str));
    }

    @Test
    public void testCPFLessThan11digits() {
        String cpf = "3334445556";
        try {
            subject = new CPFVO(cpf);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(cpf));
    }

    @Test
    public void testCPFWithMoreThan14digits() {
        String cpf = "333.444.555-666";
        try {
            subject = new CPFVO(cpf);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(cpf));
    }

    @Test
    public void testCPFWith14digits() {
        String cpf = "344.347.770-41";
        Long expected = Long.parseLong(cpf.replaceAll("\\D", ""));
        try {
            subject = new CPFVO(cpf);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertDoesNotThrow(()-> subject = new CPFVO(cpf));
        Assertions.assertEquals(expected, subject.getCpf());
    }

    @Test
    public void testCPFWith14digitsAndInvalid() {
        String cpf = "333.444.555-66";
        Long expected = Long.parseLong(cpf.replaceAll("\\D", ""));
        try {
            subject = new CPFVO(cpf);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(cpf));
    }

    @Test
    public void testCPFWith14digitsOutOfPattern() {
        String cpf = "33334444555566";
        Long expected = Long.parseLong(cpf.replaceAll("\\D", ""));
        try {
            subject = new CPFVO(cpf);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(cpf));
    }

    @Test
    public void testInvalidCPFNumber() {
        Long test = 1L;
        try {
            subject = new CPFVO(test);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> subject = new CPFVO(test));
    }

    @Test
    public void testValidCPFNumber() {
        Long test = 1234567890L;
        String expected = "01234567890";
        try {
            subject = new CPFVO(test);
        } catch (IKException e) {
            LOGGER.warn(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        subject = new CPFVO(test);
        Assertions.assertEquals(expected, subject.getTextCpf());
    }
}
