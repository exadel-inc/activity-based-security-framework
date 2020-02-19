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
