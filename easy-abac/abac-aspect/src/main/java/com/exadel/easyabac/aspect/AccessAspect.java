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

package com.exadel.easyabac.aspect;


import com.exadel.easyabac.model.exception.AbacAnnotationParseException;
import com.exadel.easyabac.model.validation.ExecutionContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Applies access restriction on methods marked by custom Access annotations.
 * Process all annotations with nested annotation {@code Access}.
 * Depends on particular {@code AccessValidator} implementation.
 *
 * @author Gleb Bondarchuk
 * @author Igor Sych
 * @since 1.0-RC1
 */
@Aspect
class AccessAspect {

    @Autowired
    private ExecutionContextBuilder contextBuilder;

    @Pointcut("execution(@(@com.exadel.easyabac.model.annotation.Access *) * *(..) ) ")
    public void methodAnnotatedNestedAnnotationOneLevelDeep() {
        //pointcut declaration, do nothing
    }

    @Pointcut("within(@(@com.exadel.easyabac.model.annotation.Access *)* )")
    public void methodWithinClassWithNestedAnnotationOneLevelDeep() {
        //pointcut declaration, do nothing
    }

    @Pointcut("execution(public * *(..)) && !execution(static * *(..))")
    public void instanceMethod() {
        //pointcut declaration, do nothing
    }

    @Before("instanceMethod() && (methodAnnotatedNestedAnnotationOneLevelDeep() || methodWithinClassWithNestedAnnotationOneLevelDeep())")
    @SuppressWarnings("unchecked")
    public void methodAnnotatedWithNestedAnnotationOneLevelDeep(JoinPoint point) {
        List<ExecutionContext> contexts;
        try {
            contexts = contextBuilder.buildContexts(point);
        } catch (Exception e) {
            throw new AbacAnnotationParseException(e);
        }
        contexts.forEach(context -> context.getValidator().validate(context));
    }
}
