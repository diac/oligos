package com.diac.oligos.authentication.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class ConnectionValidationResponse {

    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String username;
    private String token;
    private List<GrantedAuthority> authorities;
}