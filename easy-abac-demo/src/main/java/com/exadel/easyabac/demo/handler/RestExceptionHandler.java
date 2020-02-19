package com.exadel.easyabac.demo.handler;

import com.exadel.easyabac.demo.exception.AccessException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Application exception handler.
 *
 * @author Igor Sych
 * @author Gleb Bondarchuk
 * @since 1.0-RC1
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ACCESS_DENIED_PAGE = "403.html";

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDeniedException(AccessException exception, Model model) {
        model.addAttribute(exception.getResponse());
        return ACCESS_DENIED_PAGE;
    }
}
