package com.ikservices.oficinamecanica.users.infra.controller;

import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.commons.response.IKMessageType;
import com.ikservices.oficinamecanica.commons.response.IKResponse;
import com.ikservices.oficinamecanica.commons.vo.CPFVO;
import com.ikservices.oficinamecanica.users.application.gateways.PasswordHandler;
import com.ikservices.oficinamecanica.users.application.usecases.*;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.users.infra.config.TokenService;
import com.ikservices.oficinamecanica.users.infra.constants.UserConstants;
import com.ikservices.oficinamecanica.users.infra.controller.requests.CadastroUserRequest;
import com.ikservices.oficinamecanica.users.infra.controller.requests.LoginUserRequest;
import com.ikservices.oficinamecanica.users.infra.controller.requests.LoginUserResponse;
import com.ikservices.oficinamecanica.users.infra.controller.requests.UserResponse;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    private final CadastrarUsusario cadastrarUsusario;
    private final UserConverter converter;
    private final ListarUsuarios listarUsuarios;
    private final ObterUsuario obterUsuario;
    private final RemoverUsuario removerUsuario;
    private final UpdateUser updateUser;
    private final PasswordHandler passwordHandler;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private Environment environment;

    public UserController(CadastrarUsusario cadastrarUsusario,
                          UserConverter converter,
                          ListarUsuarios listarUsuarios,
                          LogarUsuario logarUsuario,
                          ObterUsuario obterUsuario,
                          UpdateUser updateUser,
                          RemoverUsuario removerUsuario,
                          PasswordHandler passwordHandler,
                          AuthenticationManager authenticationManager) {
        this.cadastrarUsusario = cadastrarUsusario;
        this.converter = converter;
        this.listarUsuarios = listarUsuarios;
        this.obterUsuario = obterUsuario;
        this.updateUser = updateUser;
        this.removerUsuario = removerUsuario;
        this.passwordHandler = passwordHandler;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<IKResponse<UserResponse>> save(@RequestBody CadastroUserRequest request, UriComponentsBuilder uriBuilder) {

        try {
            CPFVO cpfvo = new CPFVO(request.getCpf());

            User newUser = new User(cpfvo.getCpf(), request.getName(), request.getEmail(), passwordHandler.encode(request.getPassword()), request.getActive());

            User userSaved = cadastrarUsusario.execute(newUser);

            URI uri = uriBuilder.path("/user/{cpf}").buildAndExpand(userSaved.getCpf()).toUri();

            return ResponseEntity.created(uri).body(IKResponse.<UserResponse>build().body(UserResponse.parse(userSaved))
                    .addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(UserConstants.SAVE_SUCCESS_MESSAGE)));

        } catch (IKException ike) {
            LOGGER.warn(ike.getMessage(), ike);
            int httpCode = Integer.parseInt(ike.getIkMessage().getCode()) > 100 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(httpCode).body(IKResponse.<UserResponse>build().addMessage(ike.getIkMessage().getCode(),IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            if (e.getMessage().contains("is marked non-null but is null")) {
                String[] str = e.getMessage().split(" ");
                String fieldName = str[0].substring(0, 1).toUpperCase() + str[0].substring(1);
                String msg = String.format(UserConstants.USER_MANDATORY_FIELD_MESSAGE, fieldName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(IKResponse.<UserResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING, msg));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<UserResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.SAVE_ERROR_MESSAGE)));
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<IKResponse<UserResponse>> update(@RequestBody CadastroUserRequest request) {
        try {
            if (Objects.nonNull(request.getPassword()) && !request.getPassword().isEmpty()) {
                request.setPassword(passwordHandler.encode(request.getPassword()));
            }
            User updatedUser = updateUser.execute(request.toUser());
            return  ResponseEntity.ok(IKResponse.<UserResponse>build().body(UserResponse.parse(updatedUser))
                    .addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(UserConstants.UPDATE_SUCCESS_MESSAGE)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<UserResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.UPDATE_ERROR_MESSAGE)));
        }
    }

    @GetMapping
    public ResponseEntity<IKResponse<UserResponse>> userList() {
        try {
            List<User> userList = listarUsuarios.execute();
            return ResponseEntity.ok(IKResponse.<UserResponse>build().body(UserResponse.parse(userList)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(IKResponse.<UserResponse>build()
                    .addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.LIST_ERROR_MESSAGE)));
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<IKResponse<UserResponse>> getUser(@PathVariable("cpf") String cpf) {
        try {
            CPFVO cpfvo = new CPFVO(cpf);
            User user = obterUsuario.execute(cpfvo.getCpf());
            return ResponseEntity.ok(IKResponse.<UserResponse>build().body(UserResponse.parse(user)));
        } catch (IKException ike) {
            LOGGER.warn(ike.getMessage(), ike);
            int httpCode = Integer.parseInt(ike.getIkMessage().getCode()) > 100 ? Integer.parseInt(ike.getIkMessage().getCode()) : HttpStatus.EXPECTATION_FAILED.value();
            return ResponseEntity.status(httpCode).body(IKResponse.<UserResponse>build().addMessage(ike.getIkMessage().getCode(),IKMessageType.getByCode(ike.getIkMessage().getType()), ike.getIkMessage().getMessage()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    IKResponse.<UserResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.GET_ERROR_MESSAGE)));
        }
    }

    @PostMapping("login")
    public ResponseEntity<IKResponse<LoginUserResponse>> login(@RequestBody LoginUserRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authToken);

            String jwtToken = tokenService.gerarToken((UserEntity) authenticate.getPrincipal());

            LoginUserResponse loginUserResponse = LoginUserResponse.toLoginUserResponse((UserEntity) authenticate.getPrincipal()).setToken(jwtToken);

            return ResponseEntity.ok(IKResponse.<LoginUserResponse>build().body(loginUserResponse));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.ok(IKResponse.<LoginUserResponse>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.LOGIN_ERROR_MESSAGE)));
        }


    }

    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<IKResponse<String>> delete(@PathVariable("cpf") Long cpf) {
        try {
            if (removerUsuario.execute(cpf)) {
                return ResponseEntity.ok(IKResponse.<String>build().addMessage(Constants.DEFAULT_SUCCESS_CODE, IKMessageType.SUCCESS, environment.getProperty(UserConstants.DELETE_SUCCESS_MESSAGE)));
            }

            LOGGER.info(environment.getProperty(UserConstants.DELETE_ERROR_MESSAGE) + "CPF: " + cpf);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    IKResponse.<String>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.WARNING, environment.getProperty(UserConstants.DELETE_ERROR_MESSAGE)));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    IKResponse.<String>build().addMessage(Constants.DEFAULT_ERROR_CODE, IKMessageType.ERROR, environment.getProperty(UserConstants.DELETE_ERROR_MESSAGE)));
        }

    }
}
