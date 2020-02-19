package com.exadel.easyabac.demo.security.authorization.authentication;


import com.exadel.easyabac.demo.security.authorization.UserGrantedAuthority;
import com.exadel.easyabac.model.core.Action;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The example of abstract user authentication.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public abstract class AbstractUserAuthentication implements Authentication {

    protected abstract Set<Action> getUserActions();

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return getUserActions().stream().map(UserGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) {
    }
}
