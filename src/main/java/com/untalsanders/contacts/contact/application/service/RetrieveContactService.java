package com.untalsanders.contacts.contact.application.service;

import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.contact.application.port.in.RetrieveContactUseCase;
import com.untalsanders.contacts.contact.application.port.out.ContactRepository;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.shared.domain.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RetrieveContactService implements RetrieveContactUseCase {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public Result<ContactResponse, String> getContact(UUID id) {
        return Result.of(() -> contactRepository.findById(id)
                .map(contactMapper::toContactResponse)
                .orElseThrow(() -> new RuntimeException(String.format("Contact with id %s not found", id))));
    }

    @Override
    public Result<List<ContactResponse>, String> getContacts() {
        return Result.of(() -> contactMapper.toContactResponseCollection(contactRepository.findAll()));
    }
}
