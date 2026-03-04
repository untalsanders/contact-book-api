package com.untalsanders.contacts.rest.controller;

import com.untalsanders.contacts.mapper.ContactMapper;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.rest.dto.ContactDto;
import com.untalsanders.contacts.service.DeleteContactService;
import com.untalsanders.contacts.usecase.CreateContactUseCase;
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
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ContactRestController {

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
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = retrieveContactUseCase.getContact(id);
        return new ResponseEntity<>(contactMapper.toContactDto(contact.orElseThrow()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contactSaved = contactMapper.toContact(contactDto);
        createContactUseCase.createContact(contactSaved);
        return new ResponseEntity<>(contactMapper.toContactDto(contactSaved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable Long id, @RequestBody ContactDto contactDto) {
        Contact contactToUpdate = contactMapper.toContact(contactDto);
        Optional<Contact> contactUpdated = updateContactUseCase.updateContact(id, contactToUpdate);
        return new ResponseEntity<>(contactMapper.toContactDto(contactUpdated.orElseThrow()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteContactById(@PathVariable Long id) {
        deleteContactService.deleteContact(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
