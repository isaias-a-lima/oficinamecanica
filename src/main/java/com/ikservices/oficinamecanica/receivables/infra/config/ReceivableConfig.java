package com.ikservices.oficinamecanica.receivables.infra.config;

import com.ikservices.oficinamecanica.receivables.application.gatways.ReceivableRepository;
import com.ikservices.oficinamecanica.receivables.application.usecases.*;
import com.ikservices.oficinamecanica.receivables.infra.ReceivableConstant;
import com.ikservices.oficinamecanica.receivables.infra.ReceivableConverter;
import com.ikservices.oficinamecanica.receivables.infra.gateways.ReceivableRepositoryImpl;
import com.ikservices.oficinamecanica.receivables.infra.persistence.ReceivableRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(name = "receivable.properties", value = "classpath:receivable.properties", encoding = "UTF-8")
public class ReceivableConfig {
    @Autowired
    private Environment environment;

    @Bean
    public ReceivableRepository getRepository(ReceivableRepositoryJPA jpa, ReceivableConverter converter) {
        return new ReceivableRepositoryImpl(jpa, converter);
    }

    @Bean
    public ReceivableConverter getReceivableConverter() {
        return new ReceivableConverter();
    }

    @Bean
    public SaveReceivable getSaveReceivable(ReceivableRepository repository) {
        return new SaveReceivable(repository);
    }

    @Bean
    public GetReceivable getGetReceivable(ReceivableRepository repository) {
        return new GetReceivable(repository);
    }

    @Bean
    public UpdateReceivable getUpdateReceivable(ReceivableRepository repository) {
        return new UpdateReceivable(repository);
    }

    @Bean
    public ListReceivable getListReceivable(ReceivableRepository repository) {
        return new ListReceivable(repository);
    }

    @Bean
    public ListOutsourceReceivables getListOutsourceReceivable(ReceivableRepository repository) {
        return new ListOutsourceReceivables(repository);
    }

    @Bean
    public ListReceivableBySupplier getListReceivableBySupplier(ReceivableRepository repository) {
        return new ListReceivableBySupplier(repository);
    }

    @PostConstruct
    private void setupConstants() {
        ReceivableConstant.RECEIVABLE_LIST_ERROR_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_LIST_ERROR_KEY);
        ReceivableConstant.RECEIVABLE_NOT_FOUND_ERROR_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_NOT_FOUND_ERROR_KEY);
        ReceivableConstant.RECEIVABLE_SAVE_SUCCESS_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_SAVE_SUCCESS_KEY);
        ReceivableConstant.RECEIVABLE_SAVE_ERROR_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_SAVE_ERROR_KEY);
        ReceivableConstant.RECEIVABLE_GET_ERROR_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_GET_ERROR_KEY);
        ReceivableConstant.RECEIVABLE_UPDATE_SUCCESS_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_UPDATE_SUCCESS_KEY);
        ReceivableConstant.RECEIVABLE_UPDATE_ERROR_MESSAGE = environment.getProperty(ReceivableConstant.RECEIVABLE_UPDATE_ERROR_KEY);
    }
}
