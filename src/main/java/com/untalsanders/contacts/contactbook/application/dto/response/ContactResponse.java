package com.untalsanders.contacts.contactbook.application.dto.response;

public record ContactResponse(
    String id,
    String firstName,
    String lastName,
    String phoneNumber
) {}
