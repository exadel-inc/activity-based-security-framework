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

package com.exadel.easyabac.demo.security.model.project;

import com.exadel.easyabac.demo.security.validator.DemoValidator;
import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.model.validation.EntityAccessValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example of ABAC annotation to restrict project entity access.
 * This annotation will be placed to controllers or services to enable protection.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Access(identifier = ProjectId.class)
public @interface ProjectAccess {

    ProjectAction[] value();

    Class<? extends EntityAccessValidator> validator() default DemoValidator.class;
}
