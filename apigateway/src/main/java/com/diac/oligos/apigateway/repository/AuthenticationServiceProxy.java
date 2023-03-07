package com.diac.oligos.apigateway.repository;

import com.diac.oligos.apigateway.model.ConnectionValidationResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthenticationServiceProxy {

    @GetMapping(value = "/api/v1/validateConnection", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ConnectionValidationResponse> validateConnection(@RequestHeader("Authorization") String authToken);
}