package com.untalsanders.contacts.usecase;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.shared.domain.Result;

import java.util.List;

public interface RetrieveContactUseCase {
    /**
     * Retrieve a <code>Contact</code> by id.
     *
     * @param id the id to search for
     * @return the <code>Result</code> containing the <code>Contact</code> if found
     */
    Result<Contact> getContact(Long id);

    /**
     * Retrieve all <code>Contact</code>s.
     *
     * @return <code>Result</code> containing a <code>List</code> of <code>Contact</code>s
     */
    Result<List<Contact>> getContacts();
}
