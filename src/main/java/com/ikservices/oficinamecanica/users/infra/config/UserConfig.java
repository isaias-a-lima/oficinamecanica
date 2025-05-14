package com.ikservices.oficinamecanica.users.infra.config;

import com.ikservices.oficinamecanica.users.application.gateways.PasswordHandler;
import com.ikservices.oficinamecanica.users.application.gateways.UserProperties;
import com.ikservices.oficinamecanica.users.application.gateways.UserRepository;
import com.ikservices.oficinamecanica.users.application.usecases.*;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.users.infra.constants.UserConstants;
import com.ikservices.oficinamecanica.users.infra.gateways.PasswordHandlerImpl;
import com.ikservices.oficinamecanica.users.infra.gateways.UserPropertiesImpl;
import com.ikservices.oficinamecanica.users.infra.gateways.UserRepositoryJPAImpl;
import com.ikservices.oficinamecanica.users.infra.persistence.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(name = "users.properties", value = "classpath:users.properties", encoding = "UTF-8")
public class UserConfig {
    @Autowired
    Environment environment;

    @Value("${user.save.already.exists.message}")
    private String userAlreadyExistsMessage;
    @Value("${user.not.found.message}")
    private String userNotFoundMessage;
    @Value("${user.to.update.not.found.message}")
    private String userToUpdateNotFoundMessage;
    @Value("${user.mandatory.field.message}")
    private String userMandatoryFieldMessage;

    @Bean
    public UserConverter userConverter() {
        return new UserConverter();
    }

    @Bean
    public UserRepository userRepository(UserRepositoryJPA repository, UserConverter converter, UserProperties properties) {
        return new UserRepositoryJPAImpl(repository, converter, properties);
    }

    @Bean
    public CadastrarUsusario cadastrarUser(UserRepository repository, UserProperties properties){
        return new CadastrarUsusario(repository, properties);
    }

    @Bean
    public ListarUsuarios listarUsuarios(UserRepository repository) {
        return new ListarUsuarios(repository);
    }

    @Bean
    public LogarUsuario logarUsuario(UserRepository repository) {
        return new LogarUsuario(repository);
    }

    @Bean
    public UserProperties userProperties() {
        return new UserPropertiesImpl(environment);
    }

    @Bean
    public ObterUsuario obterUsuario(UserRepository repository, UserProperties properties) {
        return new ObterUsuario(repository, properties);
    }

    @Bean
    public UpdateUser atualizarUsuario(UserRepository repository, UserProperties properties) {
        return new UpdateUser(repository, properties);
    }

    @Bean
    public RemoverUsuario removerUsuario(UserRepository repository, UserProperties properties) {
        return new RemoverUsuario(repository, properties);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordHandler passwordHandler(PasswordEncoder passwordEncoder) {
        return new PasswordHandlerImpl(passwordEncoder);
    }

    @PostConstruct
    private void loadProperties() {
        UserConstants.SAVE_ERROR_ALREADY_EXISTS_MESSAGE = this.userAlreadyExistsMessage;
        UserConstants.USER_NOT_FOUND_MESSAGE = this.userNotFoundMessage;
        UserConstants.USER_TO_UPDATE_NOT_FOUND_MESSAGE = this.userToUpdateNotFoundMessage;
        UserConstants.USER_MANDATORY_FIELD_MESSAGE = this.userMandatoryFieldMessage;
    }
}
