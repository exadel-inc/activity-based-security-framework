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

package com.exadel.easyabac.demo.security.authorization.authentication;

import com.google.common.collect.ImmutableSet;

import com.exadel.easyabac.demo.security.model.project.ProjectAction;
import com.exadel.easyabac.demo.security.model.story.StoryAction;
import com.exadel.easyabac.demo.security.model.task.TaskAction;
import com.exadel.easyabac.model.core.Action;

import java.util.Set;

/**
 * Admin user authentication.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class AdminAuthentication extends AbstractUserAuthentication {

    private static final Set<Action> AVAILABLE_ACTIONS = ImmutableSet.of(
            ProjectAction.VIEW,
            ProjectAction.UPDATE,
            ProjectAction.DELETE,

            StoryAction.VIEW,

            TaskAction.VIEW
    );

    @Override
    protected Set<Action> getUserActions() {
        return AVAILABLE_ACTIONS;
    }

    @Override
    public String getName() {
        return "Administrator";
    }
}
