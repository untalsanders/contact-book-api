package com.untalsanders.contacts.contact.application.port.in;

import com.untalsanders.contacts.contact.application.dto.ContactRequest;
import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.vo.Result;

import java.util.UUID;

public interface UpdateContactUseCase {
    Result<ContactResponse, String> updateContact(UUID id, ContactRequest request);
}
