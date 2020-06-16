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

import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.model.core.Action;
import com.exadel.easyabac.model.validation.EntityAccessValidator;
import com.exadel.easyabac.model.validation.ExecutionContext;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Execution context builder.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@Component
class ExecutionContextBuilder {

    @Autowired
    private ApplicationContext applicationContext;

    List<ExecutionContext> buildContexts(JoinPoint point) {
        return getContexts(point);
    }

    private List<ExecutionContext> getContexts(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Class clazz = point.getTarget().getClass();
        Method method = signature.getMethod();

        Annotation[] methodAnnotations = method.getAnnotations();
        Annotation[] classAnnotations = clazz.getAnnotations();

        Annotation[] annotations = Stream.of(classAnnotations, methodAnnotations)
                .flatMap(Arrays::stream)
                .filter(annotation -> annotation.annotationType().getAnnotation(Access.class) != null)
                .toArray(Annotation[]::new);

        Object[] arguments = point.getArgs();
        Annotation[][] parametersAnnotations = method.getParameterAnnotations();

        Map<Class<? extends Annotation>, List<Annotation>> annotationsGroupedByType = Stream.of(annotations)
                .collect(Collectors.groupingBy(Annotation::annotationType));

        return annotationsGroupedByType.entrySet().stream()
                .map(entry -> getContext(entry.getKey(), entry.getValue(), arguments, parametersAnnotations, point))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private ExecutionContext getContext(Class<? extends Annotation> annotationType, List<Annotation> annotations, Object[] arguments, Annotation[][] parametersAnnotations, JoinPoint point) {
        Object argument = AspectUtils.getMethodArgument(arguments, parametersAnnotations, annotationType);

        Set<Action> actions = annotations.stream()
                .map(annotation -> AspectUtils.getAnnotationParameter(annotation, Access.ACTIONS_FIELD_NAME))
                .map(Action[].class::cast)
                .flatMap(Stream::of)
                .collect(Collectors.toSet());

        Set<Class<EntityAccessValidator>> validatorTypes = annotations.stream()
                .map(annotation -> AspectUtils.getAnnotationParameter(annotation, Access.VALIDATOR_FIELD_NAME))
                .map(value -> (Class<EntityAccessValidator>) value)
                .collect(Collectors.toSet());

        Class<EntityAccessValidator> validatorType = CollectionUtils.extractSingleton(validatorTypes);
        EntityAccessValidator validator = applicationContext.getBean(validatorType);

        DefaultExecutionContext context = new DefaultExecutionContext();
        context.setEntityId(argument);
        context.setRequiredActions(actions);
        context.setAccessAnnotationType(annotationType);
        context.setValidator(validator);
        context.setMethod(point.getSignature().toLongString());
        return context;
    }
}
