package com.untalsanders.contacts.contactbook.domain.exception;

public class DuplicateContactException extends ContactException {
    public DuplicateContactException(String data) {
        super("A contact with the identifier already exists: " + data);
    }
}
