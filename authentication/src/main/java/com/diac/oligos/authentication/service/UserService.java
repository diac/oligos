package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsersList();

    User encryptAndSave(User user);

    User encryptAndUpdate(User user);

    User saveOrUpdate(User user);

    void deleteEntry(User user);

    User getUserByUsrNameAndPwd(User user);

    Optional<User> getByUsername(String username);

    Optional<User> findById(String id);

    List<User> findUsersByStr(String searchStr);
}