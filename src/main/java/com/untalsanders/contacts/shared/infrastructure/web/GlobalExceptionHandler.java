package com.untalsanders.contacts.shared.infrastructure.web;

import com.untalsanders.contacts.contact.application.exception.ContactNotFoundException;
import com.untalsanders.contacts.contact.application.exception.DuplicateContactException;
import com.untalsanders.contacts.contact.application.exception.InvalidContactDataException;
import com.untalsanders.contacts.shared.domain.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${contacts.trace:false}")
    private boolean printStackTrace;

    private static final String TRACE = "trace";

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception exception, WebRequest request) {
        ApiError error = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(ErrorMessage.UNKNOWN_ERROR.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .errors(Map.of("error", exception.getMessage()))
            .build();
        if (printStackTrace && isTraceOn(request)) {
            error.setErrors(Map.of("stackTrace", ExceptionUtils.getStackTrace(exception)));
        }
        log.error(ErrorMessage.UNKNOWN_ERROR.getMessage());
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
            .errors(validationErrors.isEmpty() ? Map.of("error", exception.getMessage()) : validationErrors)
            .build();

        if (printStackTrace && isTraceOn(request)) {
            error.setErrors(Map.of("stackTrace", ExceptionUtils.getStackTrace(exception)));
        }

        log.error(ErrorMessage.VALIDATION_FAILED.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<Object> handleContactNotFoundException(ContactNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateContactException.class)
    public ResponseEntity<Object> handleDuplicateContactException(DuplicateContactException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidContactDataException.class)
    public ResponseEntity<Object> handleInvalidContactDataException(InvalidContactDataException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
