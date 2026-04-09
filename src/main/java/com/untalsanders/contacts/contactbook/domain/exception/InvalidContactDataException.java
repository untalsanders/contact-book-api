package com.untalsanders.contacts.contactbook.domain.exception;

public class InvalidContactDataException extends ContactException {
    public InvalidContactDataException(String reason) {
        super("Invalid contact data: " + reason);
    }
}
