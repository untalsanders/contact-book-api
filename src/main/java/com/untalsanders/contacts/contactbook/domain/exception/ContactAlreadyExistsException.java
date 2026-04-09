package com.untalsanders.contacts.contactbook.domain.exception;

/**
 * A class that extends <code>RuntimeException</code> to customize the error
 * message when a <code>Contact</code> already exists.
 *
 * @author Sanders Gutiérrez
 */
public class ContactAlreadyExistsException extends ContactException {
    public ContactAlreadyExistsException(String message) {
        super(message);
    }
}
