package com.diac.oligos.apigateway.repository;

import com.diac.oligos.apigateway.model.ConnectionValidationResponse;
import org.apache.http.HttpException;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class AuthenticationServiceRepository {

    public ConnectionValidationResponse validateConnection(String authToken) throws HttpException {
        try {
            WebClient client = WebClient.builder()
                    .baseUrl("lb://authentication-service")
                    .build();
            ConnectionValidationResponse response = client.get()
                    .uri("/api/v1/validateConnection")
                    .header("Authorization", authToken)
                    .retrieve()
                    .toEntity(ConnectionValidationResponse.class)
                    .flux()
                    .blockFirst()
                    .getBody();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new HttpException("Bad Gateway");
    }
}