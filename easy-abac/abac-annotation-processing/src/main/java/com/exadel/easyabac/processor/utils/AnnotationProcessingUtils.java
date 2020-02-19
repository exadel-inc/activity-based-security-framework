package com.exadel.easyabac.processor.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Annotation processing utility methods.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public class AnnotationProcessingUtils {

    private AnnotationProcessingUtils() {
        //prevent instantiation
    }

    /**
     * Gets public instance methods.
     *
     * @param clazz the clazz
     * @return the public instance methods
     */
    public static Set<ExecutableElement> getPublicInstanceMethods(TypeElement clazz) {
        if (clazz.getKind() != ElementKind.CLASS) {
            return Collections.emptySet();
        }

        List<? extends Element> elements = filterPublicInstanceMethods(clazz.getEnclosedElements());
        return elements.stream()
                .map(ExecutableElement.class::cast)
                .collect(Collectors.toSet());
    }

    private static List<? extends Element> filterPublicInstanceMethods(List<? extends Element> elements) {
        return elements.stream()
                .filter(element -> element.getKind() == ElementKind.METHOD)
                .filter(element -> element.getModifiers().contains(Modifier.PUBLIC))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .collect(Collectors.toList());

    }

    /**
     * Gets annotation parameter mirror.
     *
     * @param <T>        the type parameter
     * @param annotation the annotation
     * @param method     the method
     * @return the annotation parameter mirror
     */
    public static <T extends Annotation> TypeMirror getAnnotationParameterMirror(T annotation, Function<T, ?> method) {
        try {
            method.apply(annotation);
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        return null;
    }

    /**
     * Gets methods.
     *
     * @param element the element
     * @return the methods
     */
    public static List<Element> getMethods(Element element) {
        if (ElementUtils.isMethod(element)) {
            return Collections.singletonList(element);
        }
        if (ElementUtils.isClass(element)) {
            return element.getEnclosedElements().stream().filter(ElementUtils::isMethod).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Is annotation present boolean.
     *
     * @param annotation the annotation
     * @param element    the element
     * @return the boolean
     */
    public static boolean isAnnotationPresent(Class<? extends Annotation> annotation, Element element) {
        //annotation present directly on element
        if (element.getAnnotation(annotation) != null) {
            return true;
        }
        //check whether annotation present on another annotation the element is annotated with
        return element.getAnnotationMirrors().stream()
                .map(AnnotationMirror::getAnnotationType)
                .map(DeclaredType::asElement)
                .map(type -> type.getAnnotation(annotation))
                .anyMatch(Objects::nonNull);
    }

    public static boolean isAnnotationPresentOnMethodOrEnclosingClass(Class<? extends Annotation> annotation, ExecutableElement method) {
        return isAnnotationPresent(annotation, method) || isAnnotationPresent(annotation, method.getEnclosingElement());
    }

    /**
     * Is annotation present boolean.
     *
     * @param annotation the annotation
     * @param element    the element
     * @return the boolean
     */
    public static boolean isAnnotationPresent(TypeElement annotation, Element element) {
        return element.getAnnotationMirrors().stream().anyMatch(mirror -> mirror.getAnnotationType().equals(annotation.asType()));
    }

    /**
     * Is annotation present on any boolean.
     *
     * @param annotation the annotation
     * @param elements   the elements
     * @return the boolean
     */
    private static boolean isAnnotationPresentOnAny(TypeElement annotation, List<? extends Element> elements) {
        return CollectionUtils.isNotEmpty(elements) && elements.stream().anyMatch(element -> isAnnotationPresent(annotation, element));
    }

    /**
     * Is annotation present on method or enclosing class boolean.
     *
     * @param annotation the annotation
     * @param method     the method
     * @return the boolean
     */
    public static boolean isAnnotationPresentOnMethodOrEnclosingClass(TypeElement annotation, ExecutableElement method) {
        return isAnnotationPresent(annotation, method) || isAnnotationPresent(annotation, method.getEnclosingElement());
    }

    /**
     * Is annotation present on any method parameter boolean.
     *
     * @param annotation the annotation
     * @param method     the method
     * @return the boolean
     */
    public static boolean isAnnotationPresentOnAnyMethodParameter(TypeElement annotation, ExecutableElement method) {
        List<? extends Element> parameters = method.getParameters();
        return isAnnotationPresentOnAny(annotation, parameters);
    }
}
