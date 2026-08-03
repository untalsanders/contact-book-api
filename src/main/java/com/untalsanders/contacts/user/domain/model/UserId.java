package com.untalsanders.contacts.user.domain.model;

import com.untalsanders.contacts.shared.domain.Identifier;

public final class UserId extends Identifier {
    public UserId(String value) {
        super(validate(value));
    }

    private static String validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo ni estar vacío");
        }
        return value;
    }
}
