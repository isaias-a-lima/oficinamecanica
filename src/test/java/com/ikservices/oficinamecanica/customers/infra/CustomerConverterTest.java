//package com.ikservices.oficinamecanica.customers.infra;
//
//import com.ikservices.oficinamecanica.commons.vo.EmailVO;
//import com.ikservices.oficinamecanica.commons.vo.PhoneVO;
//import com.ikservices.oficinamecanica.customers.domain.Customer;
//import com.ikservices.oficinamecanica.customers.domain.CustomerType;
//import com.ikservices.oficinamecanica.customers.infra.controller.CustomerResponse;
//import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerEntity;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CustomerConverterTest {
//    private CustomerConverter subject;
//
//    @BeforeEach
//    public void setup() {
//        subject = new CustomerConverter();
//
//    }
//
//    @Test
//    public void testParseCustomer() {
//        Customer customer = subject.parseCustomer(getCustomerEntity());
//        Assertions.assertEquals(customer, getCustomer());
//    }
//
//    @Test
//    public void testParseCustomerList() {
//        Customer customer = getCustomer();
//        CustomerEntity customerEntity = getCustomerEntity();
//
//        List<CustomerEntity> customerEntityList = new ArrayList<>();
//        customerEntityList.add(customerEntity);
//
//        Map<Long, Customer> customerMap = subject.parseCustomerList(customerEntityList);
//
//        Assertions.assertEquals(customer, customerMap.get(customerEntity.getId()));
//    }
//
//    @Test
//    public void testCustomerResponseList() {
//        Long customerId = getCustomerEntity().getId();
//        Customer customer = getCustomer();
//
//        Map<Long, Customer> customerMap = new HashMap<>();
//        customerMap.put(customerId, customer);
//
//        CustomerResponse customerResponse = new CustomerResponse(customerId, customer);
//
//        List<CustomerResponse> responseList = subject.customerResponseList(customerMap);
//
//        Assertions.assertEquals(customerResponse, responseList.get(0));
//    }
//
//    private Customer getCustomer() {
//        CustomerEntity entity = getCustomerEntity();
//
//        Customer customer = new Customer();
//        customer.setWorkshopId(entity.getWorkshopId());
//        customer.setIdDoc(entity.getDocId());
//        customer.setName(entity.getName());
//        customer.setEmail(new EmailVO(entity.getEmail()));
//        customer.setLandline(PhoneVO.parsePhoneVO(entity.getLandline()));
//        customer.setMobilePhone(PhoneVO.parsePhoneVO(entity.getMobilePhone()));
//        customer.setType(CustomerType.getByType(entity.getType()));
//        return customer;
//    }
//
//    private CustomerEntity getCustomerEntity() {
//        CustomerEntity entity = new CustomerEntity();
//        entity.setId(1L);
//        entity.setWorkshopId(1L);
//        entity.setDocId(11122233344L);
//        entity.setName("Test");
//        entity.setEmail("test@test.com");
//        entity.setLandline("+55 11 2222-3333");
//        entity.setMobilePhone("+55 13 92222-3333");
//        entity.setType(CustomerType.PHYSICAL_PERSON.getType());
//        return entity;
//    }
//}
