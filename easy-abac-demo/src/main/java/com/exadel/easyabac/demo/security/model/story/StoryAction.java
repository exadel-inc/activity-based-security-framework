package com.exadel.easyabac.demo.security.model.story;

import com.exadel.easyabac.model.core.Action;

/**
 * Here you define your actions to restrict access to Story entity.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public enum StoryAction  implements Action {
    VIEW,
    UPDATE
}
