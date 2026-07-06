package com.untalsanders.contacts.contact.application.service;

import com.untalsanders.contacts.contact.application.dto.ContactRequest;
import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.contact.application.port.in.UpdateContactUseCase;
import com.untalsanders.contacts.contact.application.port.out.ContactRepository;
import com.untalsanders.contacts.contact.domain.Contact;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.shared.domain.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UpdateContactService implements UpdateContactUseCase {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public Result<ContactResponse, String> updateContact(UUID id, ContactRequest request) {
        return Result.of(() -> {
            Contact contact = contactMapper.toContact(request);
            if (contact.getId() == null) {
                contact.setId(id);
            }

            if (!id.equals(contact.getId())) {
                throw new IllegalArgumentException(
                    String.format("Path variable /id=%s no match with Contact[id=%s]", id, contact.getId())
                );
            }

            return contactRepository.findById(id)
                    .map(existingContact -> {
                        Contact updatedContact = contactRepository.update(id, contact);
                        return contactMapper.toContactResponse(updatedContact);
                    })
                    .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s not found", id)));
        });
    }
}
