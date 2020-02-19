package com.exadel.easyabac.aspect;

import com.exadel.easyabac.model.annotation.Access;
import com.exadel.easyabac.model.exception.AbacSystemException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * Aspect utility methods.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
class AspectUtils {

    private AspectUtils() {
        //prevent instantiation
    }

    /**
     * Gets method argument by parameters on which the {@code annotation} is present.
     *
     * @param arguments             the arguments
     * @param parametersAnnotations the parameters annotations
     * @param annotation            the annotation
     * @return the method argument
     */
    static Object getMethodArgument(Object[] arguments, Annotation[][] parametersAnnotations, Annotation annotation) {
        Access access = annotation.annotationType().getAnnotation(Access.class);
        Class<? extends Annotation> idAnnotationType = access.identifier();

        for (int i = 0; i < parametersAnnotations.length; ++i) {
            for (int j = 0; j < parametersAnnotations[i].length; ++j) {
                Annotation parameter = parametersAnnotations[i][j];
                if (idAnnotationType.isAssignableFrom(parameter.getClass())) {
                    return arguments[i];
                }
            }
        }
        return null;
    }

    /**
     * Gets annotation parameter value by provided method name.
     *
     * @param <T>        the type parameter
     * @param annotation the annotation
     * @param method     the method
     * @return the annotation parameter
     * @throws AbacSystemException in case when error during getting annotation parameter
     */
    @SuppressWarnings("unchecked")
    static <T> T getAnnotationParameter(Annotation annotation, String method) {
        try {
            return (T) annotation.getClass().getMethod(method).invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new AbacSystemException(e);
        }
    }
}
