package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.model.TokenEntity;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    List<TokenEntity> findAll();

    TokenEntity save(TokenEntity entity);

    Optional<TokenEntity> findById(String id);
}