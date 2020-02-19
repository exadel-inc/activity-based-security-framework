package com.exadel.easyabac.model.core;

/**
 * Base interface for all user actions
 * <p>
 * The example of Action implementation
 * <pre>
 * public enum Entity1Action implements Action {
 * READ,
 * WRITE;
 * }
 * </pre>
 *
 * @author Igor Sych
 * @since 1.0-RC1
 */

public interface Action {

    /**
     * Name string.
     *
     * @return the string
     */
    String name();
}
