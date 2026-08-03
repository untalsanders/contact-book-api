package com.untalsanders.contacts.shared.infrastructure.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ApiError(
    int status,
    String error,
    String path,
    LocalDateTime timestamp,
    Map<String, String> details
) {
    public static final String MSG_UNKNOWN_ERROR = "Unknown error occurred";
    public static final String MSG_VALIDATION_FAILED = "Error in validating the submitted data";

    public static ApiError of(int status, String error) {
        return ApiError.builder()
            .status(status)
            .error(error)
            .timestamp(LocalDateTime.now())
            .details(Map.of())
            .build();
    }

    public static ApiError of(int status, String error, Map<String, String> details) {
        return ApiError.builder()
            .status(status)
            .error(error)
            .timestamp(LocalDateTime.now())
            .details(details)
            .build();
    }

    public static ApiError of(int status, String error, String path) {
        return ApiError.builder()
            .status(status)
            .error(error)
            .path(path)
            .timestamp(LocalDateTime.now())
            .details(Map.of())
            .build();
    }
}
