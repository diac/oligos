package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.model.TokenEntity;
import com.diac.oligos.authentication.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TokenRedisService implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public List<TokenEntity> findAll() {
        return StreamSupport.stream(tokenRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public TokenEntity save(TokenEntity entity) {
        return tokenRepository.save(entity);
    }

    @Override
    public Optional<TokenEntity> findById(String id) {
        return tokenRepository.findById(id);
    }
}