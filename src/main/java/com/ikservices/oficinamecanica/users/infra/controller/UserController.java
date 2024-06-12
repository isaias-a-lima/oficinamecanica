package com.ikservices.oficinamecanica.users.infra.controller;

import com.ikservices.oficinamecanica.commons.response.MessageType;
import com.ikservices.oficinamecanica.commons.response.ResponseIKS;
import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.application.gateways.PasswordHandler;
import com.ikservices.oficinamecanica.users.application.usecases.*;
import com.ikservices.oficinamecanica.users.domain.User;
import com.ikservices.oficinamecanica.users.infra.UserConverter;
import com.ikservices.oficinamecanica.users.infra.config.TokenService;
import com.ikservices.oficinamecanica.users.infra.controller.requests.CadastroUserRequest;
import com.ikservices.oficinamecanica.users.infra.controller.requests.LoginUserRequest;
import com.ikservices.oficinamecanica.users.infra.controller.requests.LoginUserResponse;
import com.ikservices.oficinamecanica.users.infra.controller.requests.UserResponse;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AtualizarUsuario atualizarUsuario;
    private final PasswordHandler passwordHandler;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public UserController(CadastrarUsusario cadastrarUsusario,
                          UserConverter converter,
                          ListarUsuarios listarUsuarios,
                          LogarUsuario logarUsuario,
                          ObterUsuario obterUsuario,
                          AtualizarUsuario atualizarUsuario,
                          RemoverUsuario removerUsuario,
                          PasswordHandler passwordHandler,
                          AuthenticationManager authenticationManager) {
        this.cadastrarUsusario = cadastrarUsusario;
        this.converter = converter;
        this.listarUsuarios = listarUsuarios;
        this.obterUsuario = obterUsuario;
        this.atualizarUsuario = atualizarUsuario;
        this.removerUsuario = removerUsuario;
        this.passwordHandler = passwordHandler;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<ResponseIKS<UserResponse>> save(@RequestBody CadastroUserRequest request, UriComponentsBuilder uriBuilder) {

        User newUser = new User(request.getCpf(), request.getName(), request.getEmail(),
                passwordHandler.encode(request.getPassword()), request.getActive());

        User userSaved = cadastrarUsusario.execute(newUser);

        URI uri = uriBuilder.path("/user/{cpf}").buildAndExpand(userSaved.getCpf()).toUri();

        return ResponseEntity.created(uri).body(ResponseIKS.<UserResponse>build().body(UserResponse.parse(userSaved)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ResponseIKS<UserResponse>> update(@RequestBody CadastroUserRequest request) {
        if (Objects.nonNull(request.getPassword()) && !request.getPassword().isEmpty()) {
            request.setPassword(passwordHandler.encode(request.getPassword()));
        }
        User updatedUser = atualizarUsuario.execute(request.toUser());
        return  ResponseEntity.ok(ResponseIKS.<UserResponse>build().body(UserResponse.parse(updatedUser)));
    }

    @GetMapping
    public ResponseEntity<ResponseIKS<UserResponse>> userList() {
        List<User> userList = listarUsuarios.execute();
        return ResponseEntity.ok(ResponseIKS.<UserResponse>build().body(UserResponse.parse(userList)));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ResponseIKS<UserResponse>> getUser(@PathVariable("cpf") Long cpf) {
        User user;
        try {
            user = obterUsuario.execute(cpf);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    ResponseIKS.<UserResponse>build().addMessage(MessageType.ERROR, HttpStatus.EXPECTATION_FAILED.getReasonPhrase()));
        }
        return ResponseEntity.ok(ResponseIKS.<UserResponse>build().body(UserResponse.parse(user)));
    }

    @PostMapping("login")
    public ResponseEntity<ResponseIKS<LoginUserResponse>> login(@RequestBody LoginUserRequest request) {
        User user = null;
        LoginUserResponse loginUserResponse = null;

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authToken);

            String jwtToken = tokenService.gerarToken((UserEntity) authenticate.getPrincipal());

            loginUserResponse = LoginUserResponse.toLoginUserResponse((UserEntity) authenticate.getPrincipal()).setToken(jwtToken);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.ok(ResponseIKS.<LoginUserResponse>build().addMessage(MessageType.ERROR, "IKERRO: " + e.getMessage()));
        }

        return ResponseEntity.ok(ResponseIKS.<LoginUserResponse>build().body(loginUserResponse));
    }

    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<ResponseIKS<String>> delete(@PathVariable("cpf") Long cpf) {
        String message = removerUsuario.execute(cpf);
        return ResponseEntity.ok(ResponseIKS.<String>build().body(message).addMessage(MessageType.SUCCESS, message));
    }
}
