package com.untalsanders.contacts.service;

import com.untalsanders.contacts.usecase.CreateContactUseCase;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import org.springframework.stereotype.Service;

@Service
public class CreateContactService implements CreateContactUseCase {

    private final ContactRepository repository;

    public CreateContactService(ContactRepository contactRepository) {
        this.repository = contactRepository;
    }

    @Override
    public Result<Contact> createContact(Contact contact) {
        try {
            repository.save(contact);
            return Result.success(contact);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
