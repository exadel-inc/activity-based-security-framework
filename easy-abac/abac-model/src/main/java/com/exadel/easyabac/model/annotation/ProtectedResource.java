package com.exadel.easyabac.model.annotation;

import java.lang.annotation.*;

/**
 * Marks a class to be protected.
 * All methods of this class must be marked by at least one access control annotation.
 * {@code @PublicResource} allows to ignore this requirement.
 * @see PublicResource
 *
 * @author Igor Sych
 * @since 1.0-RC1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ProtectedResource {
}
