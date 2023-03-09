package com.diac.oligos.authentication.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.diac.oligos.authentication.dto.UserCredentialsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
public class UserCredentialsAuthFilter extends OncePerRequestFilter {

    private static final long TOKEN_EXPIRATION_TIME = 1_800_000;

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String HEADER_STRING = "Authorization";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AuthenticationProvider authenticationProvider;

    private final String jwtSecret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException {
        if (
                "/v1/sign_in".equals(request.getServletPath())
                        && HttpMethod.POST.matches(request.getMethod())
        ) {
            UserCredentialsDto userCredentialsDto = OBJECT_MAPPER.readValue(
                    request.getInputStream(),
                    UserCredentialsDto.class
            );
            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userCredentialsDto.getUsername(),
                        CharBuffer.wrap(userCredentialsDto.getPassword()),
                        Collections.emptyList()
                );
                Authentication authentication = authenticationProvider.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwtToken = JWT.create()
                        .withSubject(((User) authentication.getPrincipal()).getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                        .sign(Algorithm.HMAC512(jwtSecret.getBytes()));
                response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }
    }
}