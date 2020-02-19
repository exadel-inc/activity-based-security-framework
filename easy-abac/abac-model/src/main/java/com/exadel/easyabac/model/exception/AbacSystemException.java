package com.exadel.easyabac.model.exception;

/**
 * The root exception application can throw.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class AbacSystemException extends RuntimeException {

    /**
     * Instantiates a new Abac system exception.
     *
     * @param cause the cause
     */
    public AbacSystemException(Throwable cause) {
        super(cause);
    }
}
