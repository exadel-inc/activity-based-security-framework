package com.exadel.easyabac.demo.security.authorization;

import com.exadel.easyabac.demo.security.model.project.ProjectAction;
import com.exadel.easyabac.demo.security.model.story.StoryAction;
import com.exadel.easyabac.demo.security.model.task.TaskAction;
import com.exadel.easyabac.model.core.Action;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The demo actions provider.
 *
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
@Component
public class DemoActionProvider implements ActionProvider {

    @Override
    public Set<Action> getAvailableActions(Long entityId, Class<Action> entityClass) {
        Set<Action> actions = getUserActions();

        // this is just example of how actions can be restricted by some business dynamic attributes,
        // so one entity has the action while other entity of the same type - doesn't.
        if (entityClass.isAssignableFrom(ProjectAction.class)) {
            return filterProjectActions(actions, entityId);
        }
        if (entityClass.isAssignableFrom(StoryAction.class)) {
            return filterStoryActions(actions, entityId);
        }
        if (entityClass.isAssignableFrom(TaskAction.class)) {
            return filterTaskActions(actions, entityId);
        }
        throw new IllegalArgumentException("Action " + entityClass.getName() + " is not handled");
    }

    /**
     * Filter actions available for user for particular project with {@code projectId}
     *
     * @param actions   the actions available for user
     * @param projectId the project identifier
     * @return the available project actions
     */
    private Set<Action> filterProjectActions(Set<Action> actions, Long projectId) {
        Set<Action> projectActions = filterActions(actions, ProjectAction.class);

        // this is example of how actions can be restricted by some project attributes, for example, status, etc.
        // so, for example, projects with 'active' status are visible for developers, while others - aren't.
        if (projectId == 1L) {
            return projectActions;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Filter actions available for user for particular story with {@code storyId}
     *
     * @param actions the actions available for user
     * @param storyId the story identifier
     * @return the available story actions
     */
    private Set<Action> filterStoryActions(Set<Action> actions, Long storyId) {
        Set<Action> storyActions = filterActions(actions, StoryAction.class);

        // this is example of how actions can be restricted by some story attributes, for example, status, etc.
        // so, for example, stories with 'active' status are visible for developers, while others - aren't.
        if (storyId == 1L) {
            return storyActions;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Filter actions available for user for particular task with {@code taskId}
     *
     * @param actions the actions available for user
     * @param taskId  the task identifier
     * @return the available task actions
     */
    private Set<Action> filterTaskActions(Set<Action> actions, Long taskId) {
        Set<Action> taskActions = filterActions(actions, TaskAction.class);

        // this is example of how actions can be restricted by some task attributes, for example, status, etc.
        // so, for example, tasks with 'active' status are visible for developers, while others - aren't.
        if (taskId == 1L) {
            return taskActions;
        } else {
            return Collections.emptySet();
        }
    }

    private Set<Action> getUserActions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(UserGrantedAuthority.class::cast)
                .map(UserGrantedAuthority::getAction)
                .collect(Collectors.toSet());
    }

    private <T extends Action> Set<Action> filterActions(Set<Action> actions, Class<T> type) {
        return actions.stream().filter(type::isInstance).collect(Collectors.toSet());
    }
}



