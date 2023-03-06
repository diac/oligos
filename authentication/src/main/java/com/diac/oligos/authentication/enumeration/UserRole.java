package com.diac.oligos.authentication.enumeration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {

    USER(new HashSet<>(Arrays.asList(UserPermission.USER, UserPermission.WRITE_ONLY))),

    ADMIN(new HashSet<>(Arrays.asList(UserPermission.ADMIN)));

    Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return this.permissions.stream()
                .map(
                        userPermission -> new SimpleGrantedAuthority(userPermission.name())
                ).collect(Collectors.toSet());
    }
}