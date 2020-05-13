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

package com.exadel.easyabac.processor.base;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Abstract annotation processor.
 *
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
public abstract class AbstractAnnotationProcessor extends AbstractProcessor {

    /**
     * Get supported annotations class by the processor.
     *
     * @return the set of supported annotation types
     */
    protected abstract Set<Class<? extends Annotation>> getSupportedAnnotations();

    /**
     * Run processing on the {@code env}.
     *
     * @param env the env
     */
    protected abstract void process(RoundEnvironment env);

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return getSupportedAnnotations().stream()
                .map(Class::getCanonicalName)
                .collect(Collectors.toSet());
    }

    /**
     * Prints error message on the specified element.
     *
     * @param message message to print
     * @param element Element on which the message is bound.
     */
    protected void printErrorMessage(String message, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    /**
     * Prints error message.
     *
     * @param message message to print
     */
    private void printErrorMessage(String message) {
        printErrorMessage(message, null);
    }

    /**
     * Process the annotations against round environment {@code env}.
     * Raises the compilation error in case of any exception.
     *
     * @param annotations the annotations
     * @param env         the env
     * @return the boolean
     */
    @Override
    public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        try {
            process(env);
        } catch (Exception e) {
            String message = ExceptionUtils.getStackTrace(e);
            printErrorMessage(message);
            return true;
        }
        return false;
    }
}
