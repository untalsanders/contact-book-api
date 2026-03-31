package com.untalsanders.contacts.service;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.usecase.DeleteContactUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteContactService implements DeleteContactUseCase {
    private final ContactRepository contactRepository;

    public DeleteContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Result<Void> deleteContact(Long id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            if (contact.isEmpty()) {
                return Result.failure(String.format("Contact with id %s not found", id));
            }
            contactRepository.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
