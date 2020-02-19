package com.exadel.easyabac.demo.security.authorization;

import com.exadel.easyabac.model.core.Action;

import java.util.Set;

/**
 * Example of entity action provider.
 *
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
public interface ActionProvider {


    /**
     * Example of generic method to fetch actions by particular type.
     *
     * @param entityId    the entity identifier
     * @param entityClass the entity class
     * @return the available actions for entity
     */
    Set<Action> getAvailableActions(Long entityId, Class<Action> entityClass);
}
