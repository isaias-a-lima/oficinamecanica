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

    @Value("resource.not.found.message")
    private String resourceNotFoundMessage;
    @Value("resource.to.update.not.found.message")
    private String resourceToUpdateNotFoundMessage;

    @PostConstruct
    private void loadProperties() {
        IKConstants.RESOURCE_NOT_FOUND_MESSAGE = this.resourceNotFoundMessage;
        IKConstants.RESOURCE_TO_UPDATE_NOT_FOUND_MESSAGE = this.resourceToUpdateNotFoundMessage;
    }

}
