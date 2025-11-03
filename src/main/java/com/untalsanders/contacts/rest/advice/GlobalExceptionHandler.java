package com.untalsanders.contacts.rest.advice;

import com.untalsanders.contacts.shared.domain.ErrorMessage;
import com.untalsanders.contacts.shared.domain.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${contacts.trace:false}")
    private boolean printStackTrace;

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    private static final String TRACE = "trace";

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
        LOG.error(ErrorMessage.UNKNOWN_ERROR.getMessage(), exception);
        return buildErrorResponse(exception, ErrorMessage.UNKNOWN_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementException exception, WebRequest request) {
        LOG.error(ErrorMessage.FAILED_TO_FIND_REQUESTED_ELEMENT.getMessage(), exception);
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(SuchElementAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleSuchElementAlreadyExistsException(SuchElementAlreadyExistsException exception, WebRequest request) {
        LOG.error(ErrorMessage.ITEM_ALREADY_EXISTS.getMessage(), exception);
        return buildErrorResponse(exception, request);
    }
}
