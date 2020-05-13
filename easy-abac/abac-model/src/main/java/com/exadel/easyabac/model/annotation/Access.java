/*
 * Copyright 2019-2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
