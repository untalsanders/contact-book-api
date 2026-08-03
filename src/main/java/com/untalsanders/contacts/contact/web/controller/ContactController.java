package com.untalsanders.contacts.contact.web.controller;

import com.untalsanders.contacts.shared.infrastructure.web.dto.ApiResponse;
import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.contact.domain.port.in.CreateContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.DeleteContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.RetrieveContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.UpdateContactUseCase;
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
public class ContactController {

    private final CreateContactUseCase createContactUseCase;
    private final RetrieveContactUseCase retrieveContactUseCase;
    private final UpdateContactUseCase updateContactUseCase;
    private final DeleteContactUseCase deleteContactUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContactResponse>>> getAllContacts() {
        var contactsResult = retrieveContactUseCase.retrieveAllContacts();
        ApiResponse<List<ContactResponse>> response = ApiResponse.success(
            200,
            getOrThrow(contactsResult),
            "Successfully retrieved contacts"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(@PathVariable UUID id) {
        var contactResult = retrieveContactUseCase.findContactById(id);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            200,
            getOrThrow(contactResult),
            "Successfully retrieved contact"
        );
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        var contactResult = createContactUseCase.createContact(contactRequest);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            200,
            getOrThrow(contactResult),
            "Contact created successfully"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(@PathVariable UUID id, @Valid @RequestBody ContactRequest contactRequest) {
        var contactResult = updateContactUseCase.updateContact(id, contactRequest);
        ApiResponse<ContactResponse> responseBody = ApiResponse.success(
            200,
            getOrThrow(contactResult),
            "Contact updated successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContactById(@PathVariable UUID id) {
        var deleteResult = deleteContactUseCase.deleteContact(id);
        getOrThrow(deleteResult);
        ApiResponse<Void> responseBody = ApiResponse.success(
            200,
            null,
            "Contact deleted successfully"
        );
        return ResponseEntity.ok(responseBody);
    }

    private <T> T getOrThrow(com.untalsanders.contacts.shared.domain.Result<T> result) {
        if (!result.isSuccess()) {
            String error = result.getError();
            if (error.contains("not found")) {
                throw new com.untalsanders.contacts.contact.domain.exception.ContactNotFoundException(error);
            } else if (error.contains("already exists")) {
                throw new com.untalsanders.contacts.contact.domain.exception.DuplicateContactException(error);
            } else if (error.contains("invalid") || error.contains("must not") || error.contains("no match")) {
                throw new IllegalArgumentException(error);
            } else {
                throw new RuntimeException(error);
            }
        }
        return result.getValue();
    }
}
