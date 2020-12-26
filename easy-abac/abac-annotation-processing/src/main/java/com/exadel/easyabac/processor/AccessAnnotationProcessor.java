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
import com.exadel.easyabac.model.annotation.PublicResource;
import com.exadel.easyabac.processor.base.AbstractAnnotationProcessor;
import com.exadel.easyabac.processor.utils.AnnotationProcessingUtils;
import com.exadel.easyabac.processor.utils.ElementUtils;
import com.google.auto.service.AutoService;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.exadel.easyabac.processor.utils.AnnotationProcessingUtils.*;
import static com.google.common.collect.ImmutableSet.of;

/**
 * Annotation processor aimed to check elements annotated with {@code @Access} in conjunction with identifier-annotations.
 * Checks whether each method annotated with custom {@code @Access} have a method parameter annotation with identifier-annotation.
 * Checks whether each method with parameter annotated with identifier-annotation is annotated  with custom {@code @Access}.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@AutoService(Processor.class)
@SuppressWarnings("unused")
public class AccessAnnotationProcessor extends AbstractAnnotationProcessor {

    private static final String ID_ANNOTATION_MISSING_ERROR = "Methods annotated with @%s must have any parameter annotated with @%s.";
    private static final String ACCESS_ANNOTATION_MISSING_ERROR = "Methods with any parameter annotated with @%s must be annotated with @%s.";

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotations() {
        return of(Access.class);
    }

    @Override
    protected void process(RoundEnvironment env) {
        Set<? extends Element> annotations = env.getElementsAnnotatedWith(Access.class);

        /* Get mapping between id and access annotations. */
        List<Pair<TypeElement, TypeElement>> mapping = getAnnotationsMapping(annotations);

        /* Get methods annotated with access annotation. */
        List<ExecutableElement> methodsWithAccessAnnotation = getMethodsAnnotatedWithAccessAnnotation(mapping, env);

        /* Get methods annotated with id annotation. */
        List<ExecutableElement> methodsWithIdAnnotation = getMethodsAnnotatedWithIdAnnotation(mapping, env);

        /* Process found annotations. */
        for (Pair<TypeElement, TypeElement> pair : mapping) {
            TypeElement accessAnnotation = pair.getKey();
            TypeElement idAnnotation = pair.getValue();

            Name accessAnnotationName = accessAnnotation.getQualifiedName();
            Name isAnnotationName = idAnnotation.getQualifiedName();

            String idAnnotationMissingError = String.format(ID_ANNOTATION_MISSING_ERROR, accessAnnotationName, isAnnotationName);
            String accessAnnotationMissingError = String.format(ACCESS_ANNOTATION_MISSING_ERROR, isAnnotationName, accessAnnotationName);

            /* Find methods annotated with access annotation, but with missing id annotation, and raise compilation error. */
            methodsWithAccessAnnotation.stream()
                    .filter(method -> isAnnotationPresentOnMethodOrEnclosingClass(accessAnnotation, method))
                    .filter(method -> !isAnnotationPresentOnAnyMethodParameter(idAnnotation, method))
                    .forEach(method -> printErrorMessage(idAnnotationMissingError, method));

            /* Find methods annotated with id annotation, but with missing access annotation, and raise compilation error. */
            methodsWithIdAnnotation.stream()
                    .filter(method -> isAnnotationPresentOnAnyMethodParameter(idAnnotation, method))
                    .filter(method -> !isAnnotationPresentOnMethodOrEnclosingClass(accessAnnotation, method))
                    .forEach(method -> printErrorMessage(accessAnnotationMissingError, method));
        }
    }

    private List<ExecutableElement> getMethodsAnnotatedWithAccessAnnotation(List<Pair<TypeElement, TypeElement>> mapping, RoundEnvironment env) {
        List<TypeElement> accessAnnotations = mapping.stream().map(Pair::getKey).collect(Collectors.toList());
        return accessAnnotations.stream()
                .map(env::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .map(AnnotationProcessingUtils::getMethods)
                .flatMap(Collection::stream)
                .filter(ElementUtils::isPublicInstanceMethod)
                .map(ExecutableElement.class::cast)
                .filter(this::isMethodProtected)
                .collect(Collectors.toList());
    }

    private List<ExecutableElement> getMethodsAnnotatedWithIdAnnotation(List<Pair<TypeElement, TypeElement>> mapping, RoundEnvironment env) {
        List<TypeElement> idAnnotations = mapping.stream().map(Pair::getValue).collect(Collectors.toList());
        return idAnnotations.stream()
                .map(env::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .filter(ElementUtils::isParameter)
                .map(Element::getEnclosingElement)
                .filter(ElementUtils::isPublicInstanceMethod)
                .map(ExecutableElement.class::cast)
                .filter(this::isMethodProtected)
                .collect(Collectors.toList());
    }

    private List<Pair<TypeElement, TypeElement>> getAnnotationsMapping(Set<? extends Element> elements) {
        List<TypeElement> annotations = elements.stream().filter(ElementUtils::isAnnotation).map(TypeElement.class::cast).collect(Collectors.toList());
        return annotations.stream().map(this::getAnnotationMapping).collect(Collectors.toList());
    }

    private Pair<TypeElement, TypeElement> getAnnotationMapping(TypeElement annotation) {
        Access access = annotation.getAnnotation(Access.class);
        TypeMirror mirror = getAnnotationParameterMirror(access, Access::identifier);
        TypeElement idType = (TypeElement) processingEnv.getTypeUtils().asElement(mirror);
        return Pair.of(annotation, idType);
    }

    private boolean isMethodProtected(ExecutableElement element) {
        return !isAnnotationPresent(PublicResource.class, element);
    }
}
