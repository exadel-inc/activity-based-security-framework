package com.exadel.easyabac.demo.security.model.task;

import com.exadel.easyabac.model.core.Action;

/**
 * Here you define your actions to restrict access to Task entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public enum TaskAction implements Action {
    VIEW,
    UPDATE
}
