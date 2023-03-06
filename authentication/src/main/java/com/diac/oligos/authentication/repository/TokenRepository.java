package com.diac.oligos.authentication.repository;

import com.diac.oligos.authentication.model.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
}