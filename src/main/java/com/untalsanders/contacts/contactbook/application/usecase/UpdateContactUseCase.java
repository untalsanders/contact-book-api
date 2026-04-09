package com.untalsanders.contacts.contactbook.application.usecase;

import com.untalsanders.contacts.contactbook.application.dto.request.ContactRequest;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;

public interface UpdateContactUseCase {
    Result<ContactResponse> updateContact(Long id, ContactRequest request);
}
