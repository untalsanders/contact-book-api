package com.untalsanders.contacts.contactbook.application.usecase;

import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;

import java.util.List;
import java.util.UUID;

public interface RetrieveContactUseCase {
    /**
     * Retrieve a <code>Contact</code> by id.
     *
     * @param id the id to search for
     * @return the <code>Result</code> containing the <code>ContactResponse</code> if found
     */
    Result<ContactResponse> getContact(UUID id);

    /**
     * Retrieve all <code>Contact</code>s.
     *
     * @return <code>Result</code> containing a <code>List</code> of <code>ContactResponse</code>s
     */
    Result<List<ContactResponse>> getContacts();
}
