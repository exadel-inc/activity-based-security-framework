package com.exadel.easyabac.aspect;

import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.model.validation.EntityAccessValidator;
import com.exadel.easyabac.model.core.Action;
import com.exadel.easyabac.model.validation.ExecutionContext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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

        return Stream.of(annotations)
                .map(annotation -> getContext(annotation, arguments, parametersAnnotations, point))
                .collect(Collectors.toList());
    }

    private ExecutionContext getContext(Annotation annotation, Object[] arguments, Annotation[][] parametersAnnotations, JoinPoint point) {
        Object argument = AspectUtils.getMethodArgument(arguments, parametersAnnotations, annotation);
        Action[] actions = AspectUtils.getAnnotationParameter(annotation, Access.ACTIONS_FIELD_NAME);
        Class<EntityAccessValidator> validatorType = AspectUtils.getAnnotationParameter(annotation, Access.VALIDATOR_FIELD_NAME);
        EntityAccessValidator validator = applicationContext.getBean(validatorType);

        DefaultExecutionContext context = new DefaultExecutionContext();
        context.setEntityId(argument);
        context.setRequiredActions(Stream.of(actions).collect(Collectors.toSet()));
        context.setAccessAnnotationType(annotation.annotationType());
        context.setValidator(validator);
        context.setJoinPoint(point);
        return context;
    }
}
