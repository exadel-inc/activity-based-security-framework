package com.exadel.easyabac.model.validation;

import com.exadel.easyabac.model.core.Action;

/**
 * The entity access validator interface. Aimed to validate entity access rights.
 *
 * @param <T> the type parameter
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public interface EntityAccessValidator<T extends Action> {

    /**
     * Validates actions for the {@code context}.
     *
     * @param context the context
     */
    void validate(ExecutionContext<T> context);
}
