package com.untalsanders.contacts.service;

import com.untalsanders.contacts.usecase.DeleteContactUseCase;
import com.untalsanders.contacts.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteContactService implements DeleteContactUseCase {
    private final ContactRepository contactRepository;

    public DeleteContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
