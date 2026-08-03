package com.untalsanders.contacts.user.web.dto;

import java.time.Instant;

public record UserResponse(
    String id,
    String email,
    String username,
    Instant createdAt
) { }
