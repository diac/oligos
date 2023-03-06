package com.diac.oligos.authentication.model;

import lombok.Data;

@Data
public class JwtAuthentication {

    private String username;
    private String password;
}