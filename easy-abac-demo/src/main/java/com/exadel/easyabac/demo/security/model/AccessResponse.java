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
