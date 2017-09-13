package com.techexplore.autosuggest.controller;

import com.techexplore.autosuggest.framework.Response.AutoSuggestResponse;
import com.techexplore.autosuggest.framework.exception.AutoSuggestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalControllerExceptionHandler: Handles all exceptions at controller level so that user will be displayed with appropriate error message.
 * <p>
 * Created by chandrashekar.v on 9/13/2017.
 */

@RestControllerAdvice(basePackageClasses = AutoSuggestController.class)
public class GenericExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GenericExceptionHandler.class);

 /*   *//**
     * Handles NOT FOUND exception.
     *
     * @param ex
     * @return
     *//*
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String noHandlerFoundException(Exception ex) {
        return ex.getMessage();
    }
*/

    /**
     * Handles internal server error.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String unknownException(Exception ex) {
        return ex.getMessage();
    }

    /**
     * Handles specific type of exceptions.
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {AutoSuggestException.class, IllegalStateException.class, MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class, InvalidMediaTypeException.class, IllegalAccessException.class,
            HttpMediaTypeNotAcceptableException.class, HttpMediaTypeException.class})
    String handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        LOG.error("Error while handling request. Message: " + ex.getMessage() + " Reason:" + status.getReasonPhrase());
        AutoSuggestResponse response = new AutoSuggestResponse(ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(value = NullPointerException.class)
    String handleUnexpectedError(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        LOG.error("Unexpected error: " + ex.getMessage() + " Reason:" + status.getReasonPhrase());
        AutoSuggestResponse response = new AutoSuggestResponse(ex.getMessage());
        return ex.getMessage();
    }

    /**
     * utility method to read status code from the request.
     *
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
