package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.constants.IKConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailVOTest {

    private EmailVO subject;

    @Test
    public void testEmail(){

        String expected = "dexter-morgan@teste.com";
        subject = new EmailVO(expected);
        String mailAddress = subject.getMailAddress();
        Assertions.assertEquals(expected, mailAddress);
    }

    @Test
    public void testPattern() {
        String email = "aa9@u.co";
        boolean matches = email.matches(IKConstants.EMAIL_PATTERN);
        Assertions.assertTrue(matches);
    }
}
