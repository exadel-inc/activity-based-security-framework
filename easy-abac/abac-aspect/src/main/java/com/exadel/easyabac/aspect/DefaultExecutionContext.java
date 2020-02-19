package com.exadel.easyabac.aspect;

import com.exadel.easyabac.model.validation.EntityAccessValidator;
import com.exadel.easyabac.model.core.Action;
import com.exadel.easyabac.model.validation.ExecutionContext;

import org.aspectj.lang.JoinPoint;

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
    private JoinPoint joinPoint;

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
    public JoinPoint getJoinPoint() {
        return joinPoint;
    }

    void setJoinPoint(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    @Override
    public Class getActionType() {
        return getRequiredActions().stream()
                .map(Action::getClass)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Required actions are not defined"));
    }
}
