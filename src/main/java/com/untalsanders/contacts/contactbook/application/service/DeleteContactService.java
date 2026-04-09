package com.untalsanders.contacts.contactbook.application.service;

import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.contactbook.application.usecase.DeleteContactUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeleteContactService implements DeleteContactUseCase {
    private final ContactRepository contactRepository;

    @Override
    public Result<Void> deleteContact(UUID id) {
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
