package com.untalsanders.contacts.contact.domain.exception;

public abstract class ContactException extends RuntimeException {
    protected ContactException(String message) {
        super(message);
    }
}
