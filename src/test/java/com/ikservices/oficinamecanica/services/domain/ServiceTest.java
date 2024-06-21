package com.ikservices.oficinamecanica.services.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.workshops.domain.Workshop;

public class ServiceTest {
	private Service subject;
	
	@BeforeEach
	public void setup() {
		subject = new Service(this.getWorkshop(), "Troca de óleo", BigDecimal.valueOf(50));
	}
	
	@Test
	public void testGetters() {
		Assertions.assertEquals(this.getWorkshop(), subject.getWorkshop());
		Assertions.assertEquals("Troca de óleo", subject.getDescription());
		Assertions.assertEquals(BigDecimal.valueOf(50), subject.getValue());
	}
	
	@Test
	public void testEqualsAndHashCode() {
		Service service = new Service(this.getWorkshop(), "Troca de óleo", BigDecimal.valueOf(50));
		Assertions.assertEquals(service, subject);
	}
	
	private Workshop getWorkshop() {
		return new Workshop(this.getUser(), "Auto SB", 11222333000144L, null);
	}
	
	private User getUser() {
        return new User(11122233344L, "Nome teste", "teste@teste.com","1234", true);
    }
}
