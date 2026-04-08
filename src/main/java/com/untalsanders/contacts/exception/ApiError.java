package com.untalsanders.contacts.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private String path;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private Map<String, String> errors;

    public static ApiError of(int status, String message, String path) {
        return ApiError.builder()
            .status(status)
            .message(message)
            .path(path)
            .build();
    }
}
