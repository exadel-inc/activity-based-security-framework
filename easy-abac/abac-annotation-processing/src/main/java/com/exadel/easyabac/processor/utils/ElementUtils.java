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

package com.exadel.easyabac.processor.utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

/**
 * Utility methods for {@code Element} for simplify usage in stream API.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class ElementUtils {

    private ElementUtils() {
        //prevent instantiation
    }

    /**
     * Checks whether element is method.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean isMethod(Element element) {
        return element.getKind() == ElementKind.METHOD;
    }

    /**
     * Checks whether element is class.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean isClass(Element element) {
        return element.getKind() == ElementKind.CLASS;
    }

    /**
     * Checks whether element is annotation.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean isAnnotation(Element element) {
        return element.getKind() == ElementKind.ANNOTATION_TYPE;
    }

    /**
     * Checks whether element is parameter.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean isParameter(Element element) {
        return element.getKind() == ElementKind.PARAMETER;
    }

    /**
     * Checks whether element is public.
     *
     * @param element the element
     * @return the boolean
     */
    private static boolean isPublic(Element element) {
        return element.getModifiers().contains(Modifier.PUBLIC);
    }

    /**
     * Checks whether element is static.
     *
     * @param element the element
     * @return the boolean
     */
    private static boolean isStatic(Element element) {
        return element.getModifiers().contains(Modifier.STATIC);
    }

    /**
     * Checks whether element is method, public and non-static.
     *
     * @param element the element
     * @return the boolean
     */
    public static boolean isPublicInstanceMethod(Element element) {
        return isMethod(element) && isPublic(element) && !isStatic(element);
    }
}
