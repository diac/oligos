package com.diac.oligos.authentication.service;

import com.diac.oligos.authentication.dto.ApplicationUserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ApplicationUserDto(
                userService.getByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("Username not found")
                        )
        );
    }
}