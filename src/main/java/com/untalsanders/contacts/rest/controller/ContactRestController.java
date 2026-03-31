package com.untalsanders.contacts.rest.controller;

import com.untalsanders.contacts.exception.ContactNotFoundException;
import com.untalsanders.contacts.mapper.ContactMapper;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.rest.dto.ContactDto;
import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.usecase.CreateContactUseCase;
import com.untalsanders.contacts.usecase.DeleteContactUseCase;
import com.untalsanders.contacts.usecase.RetrieveContactUseCase;
import com.untalsanders.contacts.usecase.UpdateContactUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ContactRestController {

    private final CreateContactUseCase createContactUseCase;
    private final RetrieveContactUseCase retrieveContactUseCase;
    private final UpdateContactUseCase updateContactUseCase;
    private final DeleteContactUseCase deleteContactUseCase;
    private final ContactMapper contactMapper;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return retrieveContactUseCase.getContacts()
            .map(contactMapper::toContactDtoCollection)
            .map(ResponseEntity::ok)
            .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id) {
        return retrieveContactUseCase.getContact(id)
            .map(contactMapper::toContactDto)
            .map(ResponseEntity::ok)
            .orElseThrow(ContactNotFoundException::new);
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contact = contactMapper.toContact(contactDto);
        return createContactUseCase.createContact(contact)
            .map(contactMapper::toContactDto)
            .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
            .orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long id, @RequestBody ContactDto contactDto) {
        Contact contactToUpdate = contactMapper.toContact(contactDto);
        return updateContactUseCase.updateContact(id, contactToUpdate)
            .map(contactMapper::toContactDto)
            .map(ResponseEntity::ok)
            .orElseThrow(error -> error.contains("not found")
                ? new ContactNotFoundException(error)
                : new IllegalArgumentException(error));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContactById(@PathVariable Long id) {
        return deleteContactUseCase.deleteContact(id)
            .map(v -> {
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return ResponseEntity.ok(response);
            })
            .orElseThrow(ContactNotFoundException::new);
    }
}
