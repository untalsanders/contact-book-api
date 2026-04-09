package com.untalsanders.contacts.contactbook.application.dto.response;

public record ContactResponse(
    Long id,
    String firstName,
    String lastName,
    String phoneNumber
) {}
