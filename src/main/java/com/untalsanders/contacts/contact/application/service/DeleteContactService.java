package com.untalsanders.contacts.contact.application.service;

import com.untalsanders.contacts.contact.application.port.in.DeleteContactUseCase;
import com.untalsanders.contacts.contact.application.port.out.ContactRepository;
import com.untalsanders.contacts.shared.domain.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DeleteContactService implements DeleteContactUseCase {
    private final ContactRepository contactRepository;

    @Override
    public Result<Boolean, String> deleteContact(UUID id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.deleteById(id);
                    if (contactRepository.findById(id).isEmpty()) {
                        return Result.<Boolean, String>success(true);
                    }
                    return Result.<Boolean, String>failure("Failed to delete contact with id " + id);
                })
                .orElse(Result.failure("Contact with id " + id + " not found"));
    }
}
