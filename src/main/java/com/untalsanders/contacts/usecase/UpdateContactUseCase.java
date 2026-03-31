package com.untalsanders.contacts.usecase;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.shared.domain.Result;

public interface UpdateContactUseCase {
    Result<Contact> updateContact(Long id, Contact contact);
}
