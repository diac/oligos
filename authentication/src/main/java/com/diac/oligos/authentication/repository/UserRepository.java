package com.diac.oligos.authentication.repository;

import com.diac.oligos.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    HashSet<User> findByUsernameContaining(String username);

    HashSet<User> findByFullnameContaining(String fullname);
}