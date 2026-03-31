package com.untalsanders.contacts.service;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.usecase.UpdateContactUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateContactService implements UpdateContactUseCase {
    private final ContactRepository contactRepository;

    public UpdateContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Result<Contact> updateContact(Long id, Contact contact) {
        if (contact.getId() == null) {
            contact.setId(id);
        }

        if (!id.equals(contact.getId())) {
            return Result.failure(
                String.format("Path variable /id=%s no match with Contact[id=%s]", id, contact.getId())
            );
        }

        Optional<Contact> contactToUpdate = contactRepository.findById(id);
        if (contactToUpdate.isEmpty()) {
            return Result.failure(String.format("Contact with id %s not found", id));
        }

        try {
            return Result.success(contactRepository.update(id, contact));
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
