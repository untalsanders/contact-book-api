package com.untalsanders.contacts.contact.domain.port.in;

import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;

public interface CreateContactUseCase {
    Result<ContactResponse> createContact(ContactRequest request);
}
