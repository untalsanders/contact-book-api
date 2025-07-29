package com.untalsanders.contacts.rest.controller;

import com.untalsanders.contacts.application.service.DeleteContactService;
import com.untalsanders.contacts.application.usecase.CreateContactUseCase;
import com.untalsanders.contacts.application.usecase.RetrieveContactUseCase;
import com.untalsanders.contacts.application.usecase.UpdateContactUseCase;
import com.untalsanders.contacts.domain.model.Contact;
import com.untalsanders.contacts.infrastructure.mapper.ContactMapper;
import com.untalsanders.contacts.rest.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ContactController {

    private final CreateContactUseCase createContactUseCase;
    private final RetrieveContactUseCase retrieveContactUseCase;
    private final UpdateContactUseCase updateContactUseCase;
    private final DeleteContactService deleteContactService;
    private final ContactMapper contactMapper;

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        List<Contact> contactList = retrieveContactUseCase.getContacts();
        return new ResponseEntity<>(contactMapper.toContactDtoCollection(contactList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Contact>> getContactById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(retrieveContactUseCase.getContact(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contact = contactMapper.toContact(contactDto);
        createContactUseCase.createContact(contact);
        return new ResponseEntity<>(contactMapper.toContactDto(contact), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Contact>> updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {
        return new ResponseEntity<>(updateContactUseCase.updateContact(id, contact), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContactById(@PathVariable("id") Long id) {
        deleteContactService.deleteContact(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
