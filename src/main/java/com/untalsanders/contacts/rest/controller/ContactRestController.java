package com.untalsanders.contacts.rest.controller;

import com.untalsanders.contacts.mapper.ContactMapper;
import com.untalsanders.contacts.rest.dto.ApiResponse;
import com.untalsanders.contacts.rest.dto.ContactRequest;
import com.untalsanders.contacts.rest.dto.ContactResponse;
import com.untalsanders.contacts.usecase.CreateContactUseCase;
import com.untalsanders.contacts.usecase.DeleteContactUseCase;
import com.untalsanders.contacts.usecase.RetrieveContactUseCase;
import com.untalsanders.contacts.usecase.UpdateContactUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAllContacts() {
        var contacts = retrieveContactUseCase.getContacts();
        ApiResponse<List<ContactResponse>> responseBody = ApiResponse.success(
            contactMapper.toContactResponseCollection(contacts.getOrElse(List.of())),
            "Contacts retrieved successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(@PathVariable Long id) {
        var contact = retrieveContactUseCase.getContact(id);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactMapper.toContactResponse(contact.getOrElse(null)),
            "Contact retrieved successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        var contact = createContactUseCase.createContact(contactMapper.toContact(contactRequest));
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactMapper.toContactResponse(contact.getOrElse(null)),
            "Contact created successfully"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequest contactRequest) {
        var contactToUpdate = updateContactUseCase.updateContact(id, contactMapper.toContact(contactRequest));
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactMapper.toContactResponse(contactToUpdate.getOrElse(null)),
            "Contact updated successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContactById(@PathVariable Long id) {
        deleteContactUseCase.deleteContact(id);
        ApiResponse<Void> responseBody = ApiResponse.success(
            null,
            "Contact deleted successfully"
        );
        return ResponseEntity.ok(responseBody);
    }
}
