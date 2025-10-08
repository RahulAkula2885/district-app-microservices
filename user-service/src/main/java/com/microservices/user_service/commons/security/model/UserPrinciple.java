package com.microservices.user_service.commons.security.model;


import com.microservices.user_service.entity.User;
import com.microservices.user_service.model.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class UserPrinciple implements UserDetails {

    private final User user;
    public final Set<RoleType> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(a-> new SimpleGrantedAuthority("ROLE_"+a.name())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public Long getUserId(){
        return user.getId();
    }
}
