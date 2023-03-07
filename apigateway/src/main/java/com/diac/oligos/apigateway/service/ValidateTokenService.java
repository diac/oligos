package com.diac.oligos.apigateway.service;

import com.diac.oligos.apigateway.model.ConnectionValidationResponse;
import com.diac.oligos.apigateway.repository.AuthenticationServiceRepository;
import com.diac.oligos.apigateway.util.Util;
import lombok.AllArgsConstructor;
import org.apache.http.HttpException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValidateTokenService {

    private final AuthenticationServiceRepository authenticationServiceRepository;

    public ConnectionValidationResponse validateAuthenticationToken(String bearerToken) throws HttpException {
        if (Util.isTokenValid(bearerToken)) {
            try {
                return authenticationServiceRepository.validateConnection(bearerToken);
            } catch (Exception e) {
                throw new HttpException("Unauthorized");
            }
        }
        throw new HttpException("Unauthorized");
    }
}