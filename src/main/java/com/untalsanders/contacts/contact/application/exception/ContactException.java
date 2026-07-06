package com.untalsanders.contacts.contact.application.exception;

public abstract class ContactException extends RuntimeException {
    protected ContactException(String message) {
        super(message);
    }
}
