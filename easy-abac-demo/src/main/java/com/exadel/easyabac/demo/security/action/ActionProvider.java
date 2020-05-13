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

package com.exadel.easyabac.demo.security.action;

import com.exadel.easyabac.model.core.Action;

import java.lang.reflect.ParameterizedType;
import java.util.Set;

/**
 * Example of entity action provider.
 *
 * @param <T> the type parameter
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
public interface ActionProvider<T extends Action> {

    /**
     * Example of generic method to fetch actions by particular type.
     *
     * @param entityId the entity identifier
     * @return the available actions for entity
     */
    Set<T> getAvailableActions(Long entityId);

    /**
     * Check whether provider accepts action type.
     *
     * @param actionType the action type
     * @return true if accepts, false otherwise
     */
    @SuppressWarnings("unchecked")
    default boolean accepts(Class<?> actionType) {
        Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return actionType.equals(type);
    }
}
