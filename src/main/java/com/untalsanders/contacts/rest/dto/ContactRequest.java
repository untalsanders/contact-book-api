package com.untalsanders.contacts.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
    @NotBlank
    String firstName,

    String lastName,

    @NotBlank
    String phoneNumber
) {}
