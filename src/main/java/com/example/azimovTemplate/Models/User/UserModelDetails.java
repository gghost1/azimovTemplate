package com.example.azimovTemplate.Models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserModelDetails implements UserDetails {
    private UserModel user;

    public UserModelDetails(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // roles of current user should be returned
        // return user.getRoles().split(" "); // don't know how to cast
        return Collections.emptyList();
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    public boolean isVerified() { return user.isVerified();}

    public String getCode() { return user.getCode(); }

    public boolean isCompany() {
        return user.isCompany();
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
}
