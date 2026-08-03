package com.untalsanders.contacts.contact.web.dto;

import jakarta.validation.constraints.NotBlank;

public record ContactRequest(
    String id,

    @NotBlank
    String firstName,

    String lastName,

    @NotBlank
    String phoneNumber
) {}
