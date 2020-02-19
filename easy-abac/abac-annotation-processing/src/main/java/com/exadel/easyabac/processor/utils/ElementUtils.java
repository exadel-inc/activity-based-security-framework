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
