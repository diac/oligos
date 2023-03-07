package com.diac.oligos.apigateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionValidationResponse {

    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private List<Authority> authorities;
}