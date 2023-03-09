package com.diac.oligos.authentication.repository;

import com.diac.oligos.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserNameAndPassword(String username, String password);
}