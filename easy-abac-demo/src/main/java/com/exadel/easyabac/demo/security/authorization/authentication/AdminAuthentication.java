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
