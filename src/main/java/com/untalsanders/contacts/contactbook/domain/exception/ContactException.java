package com.untalsanders.contacts.contactbook.domain.exception;

public abstract class ContactException extends RuntimeException {
    protected ContactException(String message) {
        super(message);
    }
}
