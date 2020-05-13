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

import com.exadel.easyabac.demo.security.authorization.DemoAuthorization;
import com.exadel.easyabac.demo.security.model.project.ProjectAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * Project actions provider.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC2
 */
@Component
public class ProjectActionProvider implements ActionProvider<ProjectAction> {

    @Autowired
    private DemoAuthorization authorization;

    @Override
    public Set<ProjectAction> getAvailableActions(Long projectId) {
        Set<ProjectAction> actions = authorization.getUserActions(ProjectAction.class);

        // this is example of how actions can be restricted by some project attributes, for example, status, etc.
        // so, for example, projects with 'active' status are visible for developers, while others - aren't.

        // for example, Project project = projectDao.get(projectId), then check some project attributes or other relations
        // and decide which of actions are available.

        if (projectId == 1L) {
            return actions;
        } else {
            return Collections.emptySet();
        }
    }
}
