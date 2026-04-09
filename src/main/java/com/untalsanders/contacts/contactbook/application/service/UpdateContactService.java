package com.untalsanders.contacts.contactbook.application.service;

import com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.contactbook.application.dto.request.ContactRequest;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.contactbook.application.usecase.UpdateContactUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateContactService implements UpdateContactUseCase {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public UpdateContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public Result<ContactResponse> updateContact(Long id, ContactRequest request) {
        Contact contact = contactMapper.toContact(request);
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
            return Result.success(contactMapper.toContactResponse(contactRepository.update(id, contact)));
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
