package com.untalsanders.contacts.service;

import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.usecase.RetrieveContactUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveContactService implements RetrieveContactUseCase {
    private final ContactRepository contactRepository;

    public RetrieveContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Optional<Contact> getContact(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty()) {
            throw new ContactNotFoundException(String.format("Contact with id %s not found", id));
        }
        return contact;
    }

    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }
}
