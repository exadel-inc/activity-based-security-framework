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

import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.processor.base.AbstractAnnotationProcessor;
import com.exadel.easyabac.processor.utils.ElementUtils;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.of;

/**
 * Annotation processor aimed to validate that annotations marked as {@code Access} have right structure.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class AnnotationValidationProcessor extends AbstractAnnotationProcessor {

    private static final String METHOD_MISSING_ERROR = "%s() method is missing for @%s";

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotations() {
        return of(Access.class);
    }

    @Override
    protected void process(RoundEnvironment env) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(Access.class);
        elements.stream().map(TypeElement.class::cast).forEach(this::validate);
    }

    private void validate(TypeElement annotation) {
        List<? extends Element> elements = processingEnv.getElementUtils().getAllMembers(annotation);

        List<ExecutableElement> methods = elements.stream()
                .filter(ElementUtils::isMethod)
                .map(ExecutableElement.class::cast)
                .collect(Collectors.toList());

        validateMethodPresentOnAnnotation(annotation, methods, Access.ACTIONS_FIELD_NAME, TypeKind.ARRAY);
        validateMethodPresentOnAnnotation(annotation, methods, Access.VALIDATOR_FIELD_NAME, TypeKind.DECLARED);
    }

    private void validateMethodPresentOnAnnotation(TypeElement annotation, List<ExecutableElement> methods, String methodName, TypeKind methodReturnType) {
        Optional<ExecutableElement> valueMethod = methods.stream()
                .filter(method -> methodName.equals(method.getSimpleName().toString()))
                .filter(method -> method.getReturnType().getKind() == methodReturnType)
                .findFirst();

        if (!valueMethod.isPresent()) {
            printErrorMessage(String.format(METHOD_MISSING_ERROR, methodName, annotation), annotation);
        }
    }
}
