package com.untalsanders.contacts.contact.web.dto;

public record ContactResponse(
    String id,
    String firstName,
    String lastName,
    String phoneNumber
) {}
