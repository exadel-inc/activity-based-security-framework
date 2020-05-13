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

package com.exadel.easyabac.demo.security.model;

import com.exadel.easyabac.model.core.Action;

import java.io.Serializable;
import java.util.Set;

/**
 * Example of response saying user that some required actions to execute the REST are missing.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class AccessResponse implements Serializable {

    private static final long serialVersionUID = 5930916869380046460L;

    private String userRole;
    private Long entityId;
    private Set<Action> missingActions;
    private String resource;

    /**
     * Instantiates a new Access response.
     *
     * @param userRole       the user role
     * @param entityId       the entity id
     * @param missingActions the missing actions
     * @param resource       the resource
     */
    public AccessResponse(String userRole, Long entityId, Set<Action> missingActions, String resource) {
        this.userRole = userRole;
        this.entityId = entityId;
        this.missingActions = missingActions;
        this.resource = resource;
    }

    /**
     * Gets user role.
     *
     * @return the user role
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Gets entity id.
     *
     * @return the entity id
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Gets missing actions.
     *
     * @return the missing actions
     */
    public Set<Action> getMissingActions() {
        return missingActions;
    }

    /**
     * Gets resource.
     *
     * @return the resource
     */
    public String getResource() {
        return resource;
    }
}
