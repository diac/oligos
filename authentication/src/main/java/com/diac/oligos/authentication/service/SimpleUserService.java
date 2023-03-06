package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.model.User;
import com.diac.oligos.authentication.repository.UserRepository;
import com.diac.oligos.authentication.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    private final SequenceGeneratorService sequenceGeneratorService;

    @Value("bCryptEncoderStrength")
    private int bCryptEncoderStrength;

    @Value("userIdPrefix")
    private String userIdPrefix;

    @Value("userFormatterParam")
    private String userFormatterParam;

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User encryptAndSave(User user) {
        return saveOrUpdate(user);
    }

    @Override
    public User encryptAndUpdate(User user) {
        return saveOrUpdate(user);
    }

    @Override
    public User saveOrUpdate(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(bCryptEncoderStrength, new SecureRandom());
        String secPwd = encoder.encode(user.getPassword());
        user.setPassword(secPwd);
        user.setId(
                Util.generateId(
                        userIdPrefix,
                        userFormatterParam,
                        sequenceGeneratorService.generateSequence("Users")
                )
        );
        return userRepository.save(user);
    }

    @Override
    public void deleteEntry(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserByUsrNameAndPwd(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findUsersByStr(String searchStr) {
        HashSet<User> tmpResult = new HashSet<>();
        HashSet<User> artName = userRepository.findByUsernameContaining(searchStr);
        HashSet<User> artPreview = userRepository.findByFullnameContaining(searchStr);
        if (artName != null && artName.size() > 0) {
            tmpResult.addAll(artName);
        }
        if (artPreview != null && artPreview.size() > 0) {
            tmpResult.addAll(artPreview);
        }
        return new ArrayList<>(tmpResult);
    }
}