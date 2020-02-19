package com.exadel.easyabac.model.validation;

import com.exadel.easyabac.model.core.Action;

import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Execution context abstraction.
 *
 * @param <T> the type parameter
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0 -RC1
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
     * Gets the aspectj join point.
     *
     * @return the join point
     */
    JoinPoint getJoinPoint();

    /**
     * Gets action type.
     *
     * @return the action type
     */
    Class<T> getActionType();
}
