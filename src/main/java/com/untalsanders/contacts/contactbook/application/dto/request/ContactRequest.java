package com.untalsanders.contacts.contactbook.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
    @NotBlank
    String id,

    @NotBlank
    String firstName,

    String lastName,

    @NotBlank
    String phoneNumber
) {}
