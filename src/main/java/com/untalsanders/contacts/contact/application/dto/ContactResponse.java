package com.untalsanders.contacts.contact.application.dto;

public record ContactResponse(
    String id,
    String firstName,
    String lastName,
    String phoneNumber
) {}
