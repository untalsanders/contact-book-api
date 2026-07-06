package com.untalsanders.contacts.contact.infrastructure.web.controller;

import com.untalsanders.contacts.shared.infrastructure.web.ApiResponse;
import com.untalsanders.contacts.contact.application.dto.ContactRequest;
import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.contact.application.port.in.CreateContactUseCase;
import com.untalsanders.contacts.contact.application.port.in.DeleteContactUseCase;
import com.untalsanders.contacts.contact.application.port.in.RetrieveContactUseCase;
import com.untalsanders.contacts.contact.application.port.in.UpdateContactUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ContactRestController {

    private final CreateContactUseCase createContactUseCase;
    private final RetrieveContactUseCase retrieveContactUseCase;
    private final UpdateContactUseCase updateContactUseCase;
    private final DeleteContactUseCase deleteContactUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAllContacts() {
        var contactsResult = retrieveContactUseCase.getContacts();
        ApiResponse<List<ContactResponse>> responseBody = ApiResponse.success(
            contactsResult.getOrElse(List.of()),
            "Contacts retrieved successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(@PathVariable UUID id) {
        var contactResult = retrieveContactUseCase.getContact(id);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactResult.getOrElse(null),
            "Contact retrieved successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        var contactResult = createContactUseCase.createContact(contactRequest);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactResult.getOrElse(null),
            "Contact created successfully"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(@PathVariable UUID id, @Valid @RequestBody ContactRequest contactRequest) {
        var contactResult = updateContactUseCase.updateContact(id, contactRequest);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            contactResult.getOrElse(null),
            "Contact updated successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContactById(@PathVariable UUID id) {
        deleteContactUseCase.deleteContact(id);
        ApiResponse<Void> responseBody = ApiResponse.success(
            null,
            "Contact deleted successfully"
        );
        return ResponseEntity.ok(responseBody);
    }
}
