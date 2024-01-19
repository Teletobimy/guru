package com.ezen.guru.service;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        for (Role role : user.getRoles()) {
            collection.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getEmail() {
        return user.getEmail();
    }

    public String getPart() {
        return user.getPart();
    }
    public String getName() {
        return user.getName();
    }
    public Set<Role> getRoles() {
        return user.getRoles();
    }
    public Long getUserId(){return user.getUserId();}
}
