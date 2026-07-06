package com.untalsanders.contacts.contact.application.exception;

public class DuplicateContactException extends ContactException {
    public DuplicateContactException(String data) {
        super("A contact with the identifier already exists: " + data);
    }
}
