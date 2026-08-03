package com.untalsanders.contacts.contact.domain.exception;

public class DuplicateContactException extends ContactException {
    public DuplicateContactException(String data) {
        super("A contact with the identifier already exists: " + data);
    }
}
