package com.exadel.easyabac.demo.security.model.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Id-type annotation implementation related to {@code ProjectAccess} annotation.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ProjectId {
}
