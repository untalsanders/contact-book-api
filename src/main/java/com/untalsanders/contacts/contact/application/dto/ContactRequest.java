package com.untalsanders.contacts.contact.application.dto;

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
