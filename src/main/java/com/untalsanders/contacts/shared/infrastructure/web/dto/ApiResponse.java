package com.untalsanders.contacts.shared.infrastructure.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiResponse<T> (
    int status,
    String message,
    T data,
    LocalDateTime timestamp
) {
    public static <T> ApiResponse<T> success(int status, T data) {
        return success(status, data, null);
    }

    public static <T> ApiResponse<T> success(int status, T data, String message) {
        return ApiResponse.<T>builder()
            .status(status)
            .data(data)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
