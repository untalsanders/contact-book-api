package com.untalsanders.contacts.contact.application.exception;

public class InvalidContactDataException extends ContactException {
    public InvalidContactDataException(String reason) {
        super("Invalid contact data: " + reason);
    }
}
