package com.untalsanders.contacts.usecase;

import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.model.Contact;

import java.util.Optional;

public interface UpdateContactUseCase {
    Optional<Contact> updateContact(Long id, Contact contact) throws ContactNotFoundException;
}
