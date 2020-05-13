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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Action provider factory example.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC2
 */
@Component
public class ActionProviderFactory {

    @Autowired
    private Set<ActionProvider<?>> providers;

    public <T extends Action> ActionProvider<?> getProvider(Class<T> actionType) {
        Set<ActionProvider<?>> foundProviders = providers.stream().filter(provider -> provider.accepts(actionType)).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(foundProviders)) {
            throw new IllegalArgumentException(String.format("Unable to find provider for type %s", actionType));
        }
        if (CollectionUtils.size(foundProviders) > 1) {
            throw new IllegalArgumentException(String.format("Providers defined ambiguously for type %s", actionType));
        }
        return foundProviders.iterator().next();
    }
}
