package com.untalsanders.contacts.contact.domain.model;

/**
 * Simple POJO domain object representing a contact.
 *
 * @author Sanders Gutiérrez
 */
public record Contact(
    ContactId id,
    Name name,
    String phone
) {}
