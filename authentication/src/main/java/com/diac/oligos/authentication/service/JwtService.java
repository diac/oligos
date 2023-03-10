package com.diac.oligos.authentication.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final long TOKEN_EXPIRATION_TIME = 1_800_000;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String createJwtToken(User principal) {
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(principal.getUsername());
        jwtBuilder.withClaim("username", principal.getUsername());
        jwtBuilder.withClaim("authorities", principal.getAuthorities().toString());
        return jwtBuilder
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}