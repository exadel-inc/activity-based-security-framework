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

package com.exadel.easyabac.aspect;

import com.exadel.easyabac.model.core.Action;
import com.exadel.easyabac.model.validation.EntityAccessValidator;
import com.exadel.easyabac.model.validation.ExecutionContext;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Default execution context, the objects of {@code DefaultExecutionContext} are immutable.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
class DefaultExecutionContext implements ExecutionContext {

    private Object entityId;
    private Set<? extends Action> requiredActions;
    private EntityAccessValidator validator;
    private Class<? extends Annotation> accessAnnotationType;
    private String method;

    @Override
    public Object getEntityId() {
        return entityId;
    }

    void setEntityId(Object entityId) {
        this.entityId = entityId;
    }

    @Override
    public Set<? extends Action> getRequiredActions() {
        return requiredActions;
    }

    void setRequiredActions(Set<? extends Action> requiredActions) {
        this.requiredActions = requiredActions;
    }

    @Override
    public EntityAccessValidator getValidator() {
        return validator;
    }

    public void setValidator(EntityAccessValidator validator) {
        this.validator = validator;
    }

    @Override
    public Class<? extends Annotation> getAccessAnnotationType() {
        return accessAnnotationType;
    }

    void setAccessAnnotationType(Class<? extends Annotation> accessAnnotationType) {
        this.accessAnnotationType = accessAnnotationType;
    }

    @Override
    public String getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = method;
    }

    @Override
    public Class getActionType() {
        return getRequiredActions().stream()
                .map(Action::getClass)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Required actions are not defined"));
    }
}
