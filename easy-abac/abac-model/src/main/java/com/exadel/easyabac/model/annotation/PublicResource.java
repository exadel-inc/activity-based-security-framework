package com.exadel.easyabac.model.annotation;

import java.lang.annotation.*;

/**
 * Marking class or method with {@code @PublicAccess} allows to ignore the requirement of easy-abac protection.
 *
 * @author Igor Sych
 * @since 1.0-RC1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PublicResource {
}
