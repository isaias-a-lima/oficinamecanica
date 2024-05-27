package com.ikservices.oficinamecanica.users.infra.controller;

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

@RestController
@RequestMapping("/user")
public class UserController {

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
    public ResponseEntity<UserResponse> cadastrarUsuario(@RequestBody CadastroUserRequest request, UriComponentsBuilder uriBuilder) {

        User newUser = new User(request.getCpf(), request.getNome(), request.getEmail(),
                passwordHandler.encode(request.getSenha()));

        User userSaved = cadastrarUsusario.execute(newUser);

        URI uri = uriBuilder.path("/user/{cpf}").buildAndExpand(userSaved.getCpf()).toUri();

        return ResponseEntity.created(uri).body(converter.toUserDTO(userSaved));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserResponse> atualizarUsuario(@RequestBody CadastroUserRequest request) {
        User updatedUser = atualizarUsuario.execute(request.toUser());
        return  ResponseEntity.ok(converter.toUserDTO(updatedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listarUsuarios() {
        List<User> userList = listarUsuarios.execute();
        return ResponseEntity.ok(UserResponse.parse(userList));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UserResponse> obterUsuario(@PathVariable("cpf") Long cpf) {
        User user;
        try {
            user = obterUsuario.execute(cpf);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.ok(UserResponse.parse(user));
    }

    @PostMapping("login")
    public ResponseEntity<ResponseIKS<LoginUserResponse>> logarUsuario(@RequestBody LoginUserRequest request) {
        User user = null;

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
        Authentication authenticate = authenticationManager.authenticate(authToken);

        String jwtToken = tokenService.gerarToken((UserEntity) authenticate.getPrincipal());

        LoginUserResponse loginUserResponse = LoginUserResponse.toLoginUserResponse((UserEntity) authenticate.getPrincipal()).setToken(jwtToken);

        return ResponseEntity.ok(ResponseIKS.<LoginUserResponse>build().body(loginUserResponse));
    }

    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<String> removerUsuario(@PathVariable("cpf") Long cpf) {
        return ResponseEntity.ok(removerUsuario.execute(cpf));
    }
}
