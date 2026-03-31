package com.untalsanders.contacts.service;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
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
    public Result<Contact> getContact(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(Result::success).orElseGet(() -> Result.failure(String.format("Contact with id %s not found", id)));
    }

    @Override
    public Result<List<Contact>> getContacts() {
        return Result.success(contactRepository.findAll());
    }
}
