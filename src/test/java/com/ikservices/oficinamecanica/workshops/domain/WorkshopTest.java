//package com.ikservices.oficinamecanica.workshops.domain;
//
//import com.ikservices.oficinamecanica.users.domain.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class WorkshopTest {
//
//    private Workshop subject;
//
//    @BeforeEach
//    public void setup() {
//        subject = new Workshop(this.getUser(), "Auto SB", 11222333000144L, null);
//    }
//
//    @Test
//    public void testGetters() {
//        Assertions.assertEquals(this.getUser(), subject.getUser());
//        Assertions.assertEquals("Auto SB", subject.getName());
//        Assertions.assertEquals(11222333000144L, subject.getDocId());
//    }
//
//    @Test
//    public void testEqualsAndHashCode() {
//        Workshop autoSb = new Workshop(this.getUser(), "Auto SB", 11222333000144L, null);
//        Assertions.assertEquals(autoSb, subject);
//    }
//
//    public User getUser() {
//        return new User(11122233344L, "Nome teste", "teste@teste.com","1234", true);
//    }
//}
