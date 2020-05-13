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

package com.exadel.easyabac.demo.security.validator;

import com.exadel.easyabac.demo.exception.AccessException;
import com.exadel.easyabac.demo.security.action.ActionProvider;
import com.exadel.easyabac.demo.security.action.ActionProviderFactory;
import com.exadel.easyabac.demo.security.authorization.DemoAuthorization;
import com.exadel.easyabac.demo.security.model.AccessResponse;
import com.exadel.easyabac.model.core.Action;
import com.exadel.easyabac.model.validation.EntityAccessValidator;
import com.exadel.easyabac.model.validation.ExecutionContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * General-purpose validator example.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Component
public class DemoValidator implements EntityAccessValidator<Action> {

    private static final String ERROR_TEMPLATE = "Access to entity[id=%s] denied.";

    @Autowired
    private ActionProviderFactory actionProviderFactory;

    @Autowired
    private DemoAuthorization authorization;

    @Override
    public void validate(ExecutionContext<Action> context) {
        Long entityId = context.getEntityId();
        ActionProvider provider = actionProviderFactory.getProvider(context.getActionType());
        Set<Action> availableActions = provider.getAvailableActions(entityId);
        Set<Action> requiredActions = context.getRequiredActions();

        Set<Action> missingActions = SetUtils.difference(requiredActions, availableActions);
        if (CollectionUtils.isEmpty(missingActions)) {
            return;
        }

        AccessResponse response = new AccessResponse(
                authorization.getLoggedUserRole(),
                entityId,
                missingActions,
                context.getMethod()
        );
        throw new AccessException(String.format(ERROR_TEMPLATE, entityId), response);
    }
}
