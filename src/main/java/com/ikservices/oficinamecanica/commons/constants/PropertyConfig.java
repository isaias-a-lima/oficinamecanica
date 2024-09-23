package com.ikservices.oficinamecanica.commons.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class PropertyConfig {

    @Autowired
    private Environment environment;

    private static final String LANGUAGE = "app.language";
    private static final String COUNTRY = "app.country";

    @PostConstruct
    public void init() {
        Constants.LANGUAGE = environment.getProperty(LANGUAGE);
        Constants.COUNTRY = environment.getProperty(COUNTRY);
        Constants.LOCALE = new Locale(Constants.LANGUAGE, Constants.COUNTRY);
    }

}
