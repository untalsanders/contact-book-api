package com.untalsanders.contacts.contactbook.application.service;

import com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.contactbook.application.dto.request.ContactRequest;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.contactbook.application.usecase.CreateContactUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateContactService implements CreateContactUseCase {

    private final ContactRepository repository;
    private final ContactMapper contactMapper;

    public CreateContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.repository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public Result<ContactResponse> createContact(ContactRequest request) {
        try {
            Contact contact = contactMapper.toContact(request);
            repository.save(contact);
            return Result.success(contactMapper.toContactResponse(contact));
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}
