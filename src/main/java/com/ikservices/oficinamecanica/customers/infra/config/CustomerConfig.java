package com.ikservices.oficinamecanica.customers.infra.config;

import com.ikservices.oficinamecanica.commons.response.IKMessages;
import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.application.usecases.GetCustomer;
import com.ikservices.oficinamecanica.customers.application.usecases.ListCustomers;
import com.ikservices.oficinamecanica.customers.infra.CustomerConverter;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerMessages;
import com.ikservices.oficinamecanica.customers.infra.gateway.CustomerRepositoryImpl;
import com.ikservices.oficinamecanica.customers.infra.persistence.CustomerRepositoryJPA;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.workshops.infra.persistense.WorkshopConverter;
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
    public CustomerConverter customerConverter(WorkshopConverter workshopConverter) {
        return new CustomerConverter(workshopConverter);
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
    //TODO Maybe this Bean can moved to WorkshopConfig. Please verify.
    @Bean
    public WorkshopConverter workshopConverter(UserConverter userConverter) {
        return new WorkshopConverter(userConverter);
    }
}
