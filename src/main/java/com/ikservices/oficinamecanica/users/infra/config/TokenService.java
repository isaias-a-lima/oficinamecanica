package com.ikservices.oficinamecanica.users.infra.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ikservices.oficinamecanica.commons.constants.Constants;
import com.ikservices.oficinamecanica.users.application.UserException;
import com.ikservices.oficinamecanica.users.infra.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class TokenService {

    @Autowired
    private Environment environment;

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UserEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Oficina Mecanica")
                    .withSubject(user.getUsername())
                    .withExpiresAt(dateExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new UserException("Erro ao gerar token jwt");
        }
    }

    public String getSubject(String jwtToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Oficina Mecanica")
                    .build()
                    .verify(jwtToken)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new UserException("Token inválido ou expirado");
        }
    }

    private Instant dateExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(Constants.getTIME_ZONE()));
    }
}
