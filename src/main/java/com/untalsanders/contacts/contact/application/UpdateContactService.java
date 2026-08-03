package com.untalsanders.contacts.contact.application;

import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.contact.domain.port.in.UpdateContactUseCase;
import com.untalsanders.contacts.contact.domain.port.out.ContactRepository;
import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.web.mapper.ContactWebMapper;
import com.untalsanders.contacts.shared.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UpdateContactService implements UpdateContactUseCase {

    private final ContactRepository contactRepository;
    private final ContactWebMapper contactMapper;

    @Override
    public Result<ContactResponse> updateContact(UUID id, ContactRequest request) {
        return Result.of(() -> {
            Contact contact = contactMapper.toContact(request);
            if (contact.id() == null || contact.id().value() == null) {
                contact = new Contact(new com.untalsanders.contacts.contact.domain.model.ContactId(id.toString()), contact.name(), contact.phone());
            }

            final Contact finalContact = contact;

            if (!id.toString().equals(finalContact.id().value())) {
                throw new IllegalArgumentException(
                    String.format("Path variable /id=%s no match with Contact[id=%s]", id, finalContact.id().value())
                );
            }

            return contactRepository.findById(id)
                    .map(existingContact -> {
                        Contact updatedContact = contactRepository.update(id, finalContact);
                        return contactMapper.toContactResponse(updatedContact);
                    })
                    .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s not found", id)));
        });
    }
}
