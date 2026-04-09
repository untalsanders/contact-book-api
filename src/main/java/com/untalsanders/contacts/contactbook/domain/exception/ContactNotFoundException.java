package com.untalsanders.contacts.contactbook.domain.exception;

/**
 * A class that extends <code>RuntimeException</code> to customize the error
 * message when a <code>Contact</code> not is found.
 *
 * @author Sanders Gutiérrez
 */
public class ContactNotFoundException extends ContactException {
    public ContactNotFoundException(String id) {
        super("The contact with the identifier was not found: " + id);
    }
}
