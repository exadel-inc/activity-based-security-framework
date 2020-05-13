/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exadel.easyabac.demo.security.authorization;

import com.exadel.easyabac.model.core.Action;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Gets all user actions of type {@code actionType} available permanently.
     *
     * @param actionType the type of action
     * @return the user actions
     */
    public <T extends Action> Set<T> getUserActions(Class<T> actionType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(UserGrantedAuthority.class::cast)
                .map(UserGrantedAuthority::getAction)
                .filter(actionType::isInstance)
                .map(actionType::cast)
                .collect(Collectors.toSet());
    }
}
