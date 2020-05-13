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

package com.exadel.easyabac.model.validation;

import com.exadel.easyabac.model.core.Action;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Execution context abstraction.
 *
 * @param <T> the type parameter
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
public interface ExecutionContext<T extends Action> {

    /**
     * Gets entity id.
     *
     * @param <I> the type parameter
     * @return the entity id
     */
    <I> I getEntityId();

    /**
     * Gets required actions.
     *
     * @return the required actions
     */
    Set<T> getRequiredActions();

    /**
     * Gets entity access validator.
     *
     * @return the validator
     */
    EntityAccessValidator<T> getValidator();

    /**
     * Gets access annotation type.
     *
     * @return the access annotation type
     */
    Class<Annotation> getAccessAnnotationType();

    /**
     * Gets the executing method
     *
     * @return the method
     */
    String getMethod();

    /**
     * Gets action type.
     *
     * @return the action type
     */
    Class<T> getActionType();
}
