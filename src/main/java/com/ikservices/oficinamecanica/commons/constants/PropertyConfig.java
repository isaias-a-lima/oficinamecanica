package com.ikservices.oficinamecanica.commons.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Configuration
@PropertySource(name = "autorepairshop.properties", value = "classpath:autorepairshop.properties", encoding = "UTF-8")
public class PropertyConfig {

    @Value("password.null.or.empty.message")
    private String passwordNullOrEmptyMessage;
    @Value("password.invalid.message")
    private String passwordInvalidMessage;
    @Value("password.no.latin.message")
    private String passwordNoLatinMessage;
    @Value("resource.not.found.message")
    private String resourceNotFoundMessage;
    @Value("resource.to.update.not.found.message")
    private String resourceToUpdateNotFoundMessage;
    @Value("empty.list.message")
    private String emptyListMessage;

    @Value("expired.deletion.date")
    private String expiredDeletionDate;

    @PostConstruct
    private void loadProperties() {
        IKConstants.PASSWORD_NULL_OR_EMPTY_MESSAGE = this.passwordNullOrEmptyMessage;
        IKConstants.PASSWORD_INVALID_MESSAGE = this.passwordInvalidMessage;
        IKConstants.PASSWORD_NO_LATIN_MESSAGE = this.passwordNoLatinMessage;
        IKConstants.RESOURCE_NOT_FOUND_MESSAGE = this.resourceNotFoundMessage;
        IKConstants.RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE = this.resourceToUpdateNotFoundMessage;
        IKConstants.EMPTY_LIST_MESSAGE = this.emptyListMessage;
        IKConstants.EXPIRED_DELETE_DATE = this.expiredDeletionDate;
    }

}
