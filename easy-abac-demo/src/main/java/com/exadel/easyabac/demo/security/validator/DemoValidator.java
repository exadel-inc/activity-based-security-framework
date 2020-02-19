package com.exadel.easyabac.demo.security.validator;

import com.exadel.easyabac.demo.exception.AccessException;
import com.exadel.easyabac.demo.security.authorization.ActionProvider;
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
    private ActionProvider actionProvider;

    @Autowired
    private DemoAuthorization authorization;

    @Override
    public void validate(ExecutionContext<Action> context) {
        Long entityId = context.getEntityId();
        Set<Action> availableActions = actionProvider.getAvailableActions(entityId, context.getActionType());
        Set<Action> requiredActions = context.getRequiredActions();

        Set<Action> missingActions = SetUtils.difference(requiredActions, availableActions);
        if (CollectionUtils.isEmpty(missingActions)) {
            return;
        }

        AccessResponse response = new AccessResponse(
                authorization.getLoggedUserRole(),
                entityId,
                missingActions,
                context.getJoinPoint().getSignature().toString()
        );
        throw new AccessException(String.format(ERROR_TEMPLATE, entityId), response);
    }
}
