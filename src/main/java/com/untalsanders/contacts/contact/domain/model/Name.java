package com.untalsanders.contacts.contact.domain.model;

/**
 * Value Object representing a person's name.
 *
 * @author Sanders Gutiérrez
 */
public record Name(String first, String last) {
    public Name {
        if (first == null || first.isBlank()) {
            throw new IllegalArgumentException("The first name cannot be null or blank");
        }
        if (last == null || last.isBlank()) {
            throw new IllegalArgumentException("The last name cannot be null or blank");
        }
    }
}
