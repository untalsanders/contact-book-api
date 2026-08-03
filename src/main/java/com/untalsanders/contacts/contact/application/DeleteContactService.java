package com.untalsanders.contacts.contact.application;

import com.untalsanders.contacts.contact.domain.port.in.DeleteContactUseCase;
import com.untalsanders.contacts.contact.domain.port.out.ContactRepository;
import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.shared.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeleteContactService implements DeleteContactUseCase {
    private final ContactRepository contactRepository;

    @Override
    public Result<Boolean> deleteContact(UUID id) {
        return Result.of(() -> {
            Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Contact with id " + id + " not found"));
            contactRepository.deleteById(id);
            return true;
        });
    }
}
