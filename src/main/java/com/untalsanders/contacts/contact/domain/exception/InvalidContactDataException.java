package com.untalsanders.contacts.contact.domain.exception;

public class InvalidContactDataException extends ContactException {
    public InvalidContactDataException(String reason) {
        super("Invalid contact data: " + reason);
    }
}
