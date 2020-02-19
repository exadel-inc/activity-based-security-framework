package com.exadel.easyabac.demo.security.authorization;

import com.exadel.easyabac.model.core.Action;

import org.springframework.security.core.GrantedAuthority;

/**
 * User granted authority.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class UserGrantedAuthority implements GrantedAuthority {

    private Action action;

    /**
     * Instantiates a new User granted authority.
     *
     * @param action the action
     */
    public UserGrantedAuthority(Action action) {
        this.action = action;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    @Override
    public String getAuthority() {
        return action.name();
    }
}
