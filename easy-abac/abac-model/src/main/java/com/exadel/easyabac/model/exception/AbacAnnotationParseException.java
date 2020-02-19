package com.exadel.easyabac.model.exception;

/**
 * Exception during parsing abac annotations.
 *
 * @author Gleb Bondarchuk
 * @since 25.09.2019
 */
public class AbacAnnotationParseException extends AbacSystemException {

    /**
     * Instantiates a new Abac system exception.
     *
     * @param cause the cause
     */
    public AbacAnnotationParseException(Throwable cause) {
        super(cause);
    }
}
