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
     * @param annotationType        the annotation type
     * @return the method argument
     */
    static Object getMethodArgument(Object[] arguments, Annotation[][] parametersAnnotations, Class<? extends Annotation> annotationType) {
        Access access = annotationType.getAnnotation(Access.class);
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
