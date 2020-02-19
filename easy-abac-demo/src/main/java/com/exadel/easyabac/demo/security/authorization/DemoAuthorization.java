package com.exadel.easyabac.demo.security.authorization;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

/**
 * Demo authorization utility methods.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Component
public class DemoAuthorization {

    public void loginAs(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Logout.
     */
    public void logout() {
        loginAs(null);
    }

    /**
     * Gets logged user role.
     *
     * @return the logged user role
     */
    public String getLoggedUserRole() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Principal::getName)
                .orElse(null);
    }
}
