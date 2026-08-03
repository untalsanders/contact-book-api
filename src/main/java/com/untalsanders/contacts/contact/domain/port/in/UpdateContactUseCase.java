package com.untalsanders.contacts.contact.domain.port.in;

import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;

import java.util.UUID;

public interface UpdateContactUseCase {
    Result<ContactResponse> updateContact(UUID id, ContactRequest request);
}
