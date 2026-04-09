package com.untalsanders.contacts.shared.web;

import com.untalsanders.contacts.contactbook.domain.exception.ContactNotFoundException;
import com.untalsanders.contacts.contactbook.domain.exception.ContactAlreadyExistsException;
import com.untalsanders.contacts.shared.domain.ErrorMessage;
import com.untalsanders.contacts.shared.domain.ErrorResponse;
import com.untalsanders.contacts.shared.web.exception.SuchElementAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiError> handleGeneralException(Exception exception, HttpServletRequest request) {
        ApiError error = ApiError.of(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage(),
            request.getRequestURI()
        );
        if (printStackTrace && isTraceOn((WebRequest) request)) {
            error.setErrors(Map.of("stackTrace", ExceptionUtils.getStackTrace(exception)));
            LOG.error(ErrorMessage.UNKNOWN_ERROR.getMessage(), exception);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        Map<String, String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value",
                (oldValue, newValue) -> newValue
            ));
        ApiError error = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(ErrorMessage.VALIDATION_FAILED.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .errors(validationErrors)
            .build();
        LOG.error(ErrorMessage.VALIDATION_FAILED.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementException exception, WebRequest request) {
        LOG.error(ErrorMessage.FAILED_TO_FIND_REQUESTED_ELEMENT.getMessage(), exception);
        return buildErrorResponse(exception, request);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException exception, WebRequest request) {
        LOG.error(ErrorMessage.FAILED_TO_FIND_REQUESTED_ELEMENT.getMessage(), exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ContactAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleContactAlreadyExistsException(ContactAlreadyExistsException exception, WebRequest request) {
        LOG.error(ErrorMessage.ITEM_ALREADY_EXISTS.getMessage(), exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        LOG.error(exception.getMessage(), exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SuchElementAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleSuchElementAlreadyExistsException(SuchElementAlreadyExistsException exception, WebRequest request) {
        LOG.error(ErrorMessage.ITEM_ALREADY_EXISTS.getMessage(), exception);
        return buildErrorResponse(exception, request);
    }
}
