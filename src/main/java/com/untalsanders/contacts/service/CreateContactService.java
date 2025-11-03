package com.untalsanders.contacts.service;

import com.untalsanders.contacts.usecase.CreateContactUseCase;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateContactService implements CreateContactUseCase {

    private final ContactRepository repository;

    public CreateContactService(ContactRepository contactRepository) {
        this.repository = contactRepository;
    }

    @Override
    public void createContact(Contact contact) {
        repository.save(contact);
    }
}
