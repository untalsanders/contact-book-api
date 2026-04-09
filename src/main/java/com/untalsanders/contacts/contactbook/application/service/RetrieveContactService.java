package com.untalsanders.contacts.contactbook.application.service;

import com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.contactbook.application.usecase.RetrieveContactUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetrieveContactService implements RetrieveContactUseCase {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public RetrieveContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public Result<ContactResponse> getContact(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(c -> Result.success(contactMapper.toContactResponse(c)))
                .orElseGet(() -> Result.failure(String.format("Contact with id %s not found", id)));
    }

    @Override
    public Result<List<ContactResponse>> getContacts() {
        return Result.success(contactMapper.toContactResponseCollection(contactRepository.findAll()));
    }
}
