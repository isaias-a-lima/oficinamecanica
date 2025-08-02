package com.ikservices.oficinamecanica.commons.vo;

import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.utils.IKLoggerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

public class PasswordVOTest {

    private static final Logger LOGGER = IKLoggerUtil.getLogger(PasswordVOTest.class);

    private PasswordVO subject;

    @Test
    public void testPassword() {
        String pass = "*Is1234";
        Assertions.assertDoesNotThrow(()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoSpecialCharacter() {
        String pass = "Is1234";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoCapitalLetters() {
        String pass = "*is1234";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoLowerCaseLetters() {
        String pass = "*IS1234";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoNumber() {
        String pass = "*Isaias";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoLetters() {
        String pass = "*12345";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }

    @Test
    public void testPasswordNoLatin() {
        String pass = "अना सिल्";
        try {
            new PasswordVO(pass);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        Assertions.assertThrows(IKException.class, ()-> new PasswordVO(pass));
    }
}
