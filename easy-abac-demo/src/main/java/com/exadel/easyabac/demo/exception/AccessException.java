package com.exadel.easyabac.demo.exception;

import com.exadel.easyabac.demo.security.model.AccessResponse;

/**
 * The resource access exception.
 *
 * @author Igor Sych
 * @since 1.0-RC1
 */
public class AccessException extends RuntimeException {

    private final AccessResponse response;

    /**
     * Instantiates a new Access exception.
     *
     * @param message the message
     */
    public AccessException(String message, AccessResponse response) {
        super(message);
        this.response = response;
    }

    public AccessResponse getResponse() {
        return response;
    }
}
