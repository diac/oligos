package com.diac.oligos.authentication.filter;

import com.diac.oligos.authentication.model.ConnectionValidationResponse;
import com.diac.oligos.authentication.model.JwtAuthentication;
import com.diac.oligos.authentication.model.TokenEntity;
import com.diac.oligos.authentication.service.TokenService;
import com.diac.oligos.authentication.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwtIssuer}")
    private String jwtIssuer;

    @Value("${securityKey}")
    private String securityKey;

    private final AuthenticationManager authenticationManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TokenService tokenService;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            JwtAuthentication jwtAuthentication = objectMapper.readValue(
                    request.getInputStream(),
                    JwtAuthentication.class
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    jwtAuthentication.getUsername(),
                    jwtAuthentication.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setIssuer(jwtIssuer)
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
        TokenEntity tokenEntity = tokenService.save(
                TokenEntity.builder()
                        .id(Util.generateUuid())
                        .authenticationToken(token)
                        .username(authResult.getName())
                        .createdBy("SYSTEM")
                        .createdOn(LocalDateTime.now())
                        .modifiedBy("SYSTEM")
                        .modifiedOn(LocalDateTime.now())
                        .build()
        );
        response.addHeader("Authorization", String.format("Bearer %s", tokenEntity.getId()));
        response.addHeader("Expiration", String.valueOf(30 * 60));
        ConnectionValidationResponse connectionValidationResponse = ConnectionValidationResponse.builder()
                .status(HttpStatus.OK.name())
                .token(String.format("Bearer %s", tokenEntity.getId()))
                .methodType(HttpMethod.GET.name())
                .isAuthenticated(true)
                .build();
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(connectionValidationResponse));
    }
}