package com.untalsanders.contacts.contact.application;

import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.contact.domain.port.in.RetrieveContactUseCase;
import com.untalsanders.contacts.contact.domain.port.out.ContactRepository;
import com.untalsanders.contacts.contact.web.mapper.ContactWebMapper;
import com.untalsanders.contacts.shared.domain.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RetrieveContactService implements RetrieveContactUseCase {
    private final ContactRepository contactRepository;
    private final ContactWebMapper contactMapper;

    @Override
    public Result<ContactResponse> findContactById(UUID id) {
        try {
            return contactRepository.findById(id)
                .map(contactMapper::toContactResponse)
                .map(Result::success)
                .orElseGet(() -> Result.failure(String.format("Contact with id %s not found", id)));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve contact: " + e.getMessage());
        }
    }

    @Override
    public Result<List<ContactResponse>> retrieveAllContacts() {
        try {
            return Result.success(contactMapper.toContactResponseCollection(contactRepository.findAll()));
        } catch (Exception e) {
            return Result.failure("Failed to retrieve contacts: " + e.getMessage());
        }
    }
}
