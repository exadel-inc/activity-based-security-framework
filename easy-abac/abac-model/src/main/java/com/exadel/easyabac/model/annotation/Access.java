package com.exadel.easyabac.model.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base annotation for custom Access annotations. All custom annotations must contain {@code @Access}.
 * Implementations of this annotation determine the point at which the check will be executed
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Access {

    /**
     * Actions field name in annotations
     */
    String ACTIONS_FIELD_NAME = "value";
    /**
     * Validator field name in annotations
     */
    String VALIDATOR_FIELD_NAME = "validator";

    /**
     * The annotation that indicates identifier
     *
     * @return class of id-type annotation
     */
    Class<? extends Annotation> identifier();
}
