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

package com.exadel.easyabac.processor;


import static com.exadel.easyabac.processor.utils.AnnotationProcessingUtils.*;
import static com.google.common.collect.ImmutableSet.of;

import com.google.auto.service.AutoService;

import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.model.annotation.ProtectedResource;
import com.exadel.easyabac.model.annotation.PublicResource;
import com.exadel.easyabac.processor.base.AbstractAnnotationProcessor;
import com.exadel.easyabac.processor.utils.AnnotationProcessingUtils;
import com.exadel.easyabac.processor.utils.ElementUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;


/**
 * Annotation processor aimed to check that each element annotated with {@code @ProtectedResource}
 * and not annotated with {@code @PublicResource} is annotated  with at least one type which specifies access to the method.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@AutoService(Processor.class)
public class ProtectAccessProcessor extends AbstractAnnotationProcessor {

    private static final String ERROR_MESSAGE = "Class annotated with @" + ProtectedResource.class.getName() +
            " should have each instance methods either annotated with @" + PublicResource.class.getName() +
            " or annotated with at least one type which specifies access to the method (see @" + Access.class.getName() + " annotation).";

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotations() {
        return of(ProtectedResource.class, PublicResource.class, Access.class);
    }

    @Override
    protected void process(RoundEnvironment env) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(ProtectedResource.class);

        Set<ExecutableElement> methods = elements.stream()
                .filter(ElementUtils::isClass)
                .map(TypeElement.class::cast)
                .map(AnnotationProcessingUtils::getPublicInstanceMethods)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        Set<ExecutableElement> invalidMethods = methods.stream()
                .filter(method -> !isAnnotationPresent(PublicResource.class, method))
                .filter(method -> !isAnnotationPresentOnMethodOrEnclosingClass(Access.class, method))
                .collect(Collectors.toSet());

        invalidMethods.forEach(method -> printErrorMessage(ERROR_MESSAGE, method));
    }
}
