package com.ikservices.oficinamecanica.commons.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Configuration
@PropertySource(name = "users.properties", value = "classpath:autorepairshop.properties")
public class PropertyConfig {



}
