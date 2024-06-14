package com.ikservices.oficinamecanica.customers.infra.config;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerRepositoryImpl;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerRepositoryJPA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public CustomerRepository customerRepository(CustomerRepositoryJPA customerRepositoryJPA, CustomerConverter converter) {
        return new CustomerRepositoryImpl(customerRepositoryJPA, converter);
    }

    @Bean
    public CustomerConverter customerConverter() {
        return new CustomerConverter();
    }

    @Bean
    public ListCustomers listCustomers(CustomerRepository repository) {
        return new ListCustomers(repository);
    }
}
