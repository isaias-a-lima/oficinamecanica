package com.ikservices.oficinamecanica.customers.infra.config;

import com.ikservices.oficinamecanica.commons.response.IKMessages;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.application.usecases.GetCustomer;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerMessages;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerRepositoryImpl;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(name = "customers.properties", value = "classpath:customers.properties", encoding = "UTF-8")
public class CustomerConfig {
    @Autowired
    Environment environment;

    @Bean
    public CustomerRepository customerRepository(CustomerRepositoryJPA customerRepositoryJPA, CustomerConverter converter) {
        return new CustomerRepositoryImpl(customerRepositoryJPA, converter);
    }

    @Bean
    public CustomerConverter customerConverter() {
        return new CustomerConverter();
    }

    @Bean
    public ListCustomers listCustomers(CustomerRepository repository, CustomerMessages customerProperties) {
        return new ListCustomers(repository, customerProperties);
    }

    @Bean
    public GetCustomer getCustomer(CustomerRepository repository) {
        return new GetCustomer(repository);
    }

    @Bean
    public CustomerMessages customerMessages() {
        return new CustomerMessages(environment);
    }
}
