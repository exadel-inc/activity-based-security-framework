package com.exadel.easyabac.demo.security.model.project;

import com.exadel.easyabac.model.core.Action;

/**
 * Here you define your actions to restrict access to Project entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public enum ProjectAction implements Action {
    VIEW,
    UPDATE,
    DELETE
}
