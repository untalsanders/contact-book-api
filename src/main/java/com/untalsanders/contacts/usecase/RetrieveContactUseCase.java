package com.untalsanders.contacts.usecase;

import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.model.Contact;

import java.util.List;
import java.util.Optional;

public interface RetrieveContactUseCase {
    /**
     * Retrieve a <code>Contact</code> by id.
     *
     * @param id the id to search for
     * @return the <code>Contact</code> if found
     */
    Optional<Contact> getContact(Long id) throws ContactNotFoundException;

    /**
     * Retrieve all <code>Contact</code>s.
     *
     * @return <code>Collection</code> of <code>Contact</code>s
     */
    List<Contact> getContacts();
}
