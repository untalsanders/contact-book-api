package com.untalsanders.contacts.contact.application;

import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.contact.domain.port.in.CreateContactUseCase;
import com.untalsanders.contacts.contact.domain.port.out.ContactRepository;
import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.web.mapper.ContactWebMapper;
import com.untalsanders.contacts.shared.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CreateContactService implements CreateContactUseCase {

    private final ContactRepository repository;
    private final ContactWebMapper contactMapper;

    @Override
    public Result<ContactResponse> createContact(ContactRequest request) {
        return Result.of(() -> {
            Contact contact = contactMapper.toContact(request);
            Contact savedContact = repository.save(contact);
            return contactMapper.toContactResponse(savedContact);
        });
    }
}
