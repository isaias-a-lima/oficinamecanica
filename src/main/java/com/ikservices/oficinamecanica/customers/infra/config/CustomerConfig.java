package com.ikservices.oficinamecanica.customers.infra.config;

import com.ikservices.oficinamecanica.customers.application.gateways.CustomerRepository;
import com.ikservices.oficinamecanica.customers.application.usecases.*;
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
    public CustomerRepository customerRepository(CustomerRepositoryJPA customerRepositoryJPA) {
        return new CustomerRepositoryImpl(customerRepositoryJPA);
    }

    @Bean
    public ListCustomers listCustomers(CustomerRepository repository) {
        return new ListCustomers(repository);
    }

    @Bean
    public GetCustomer getCustomer(CustomerRepository repository) {
        return new GetCustomer(repository);
    }

    @Bean
    public SaveCustomer saveCustomer(CustomerRepository repository) {
        return new SaveCustomer(repository);
    }

    @Bean
    public UpdateCustomer updateCustomer(CustomerRepository repository) {
    	return new UpdateCustomer(repository);
    }
    
    //TODO Maybe this Bean can moved to WorkshopConfig. Please verify.
    @Bean
    public WorkshopConverter workshopConverter(UserConverter userConverter) {
        return new WorkshopConverter(userConverter);
    }

    @Bean
    public GetCustomerByVeclicle getCustomerByVeclicle(CustomerRepository repository) {
        return new GetCustomerByVeclicle(repository);
    }
}
