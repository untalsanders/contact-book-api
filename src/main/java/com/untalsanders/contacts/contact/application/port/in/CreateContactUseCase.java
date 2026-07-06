package com.untalsanders.contacts.contact.application.port.in;

import com.untalsanders.contacts.contact.application.dto.ContactRequest;
import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.vo.Result;

public interface CreateContactUseCase {
    Result<ContactResponse, String> createContact(ContactRequest request);
}
