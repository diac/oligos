package com.diac.oligos.authentication.dto;

import com.diac.oligos.authentication.enumeration.UserRole;
import com.diac.oligos.authentication.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@Data
@RequiredArgsConstructor
public class ApplicationUserDto implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.isAdmin()
                ? UserRole.ADMIN.getAuthorities()
                : UserRole.USER.getAuthorities();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsExpiryDate() == null
                || user.getCredentialsExpiryDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}