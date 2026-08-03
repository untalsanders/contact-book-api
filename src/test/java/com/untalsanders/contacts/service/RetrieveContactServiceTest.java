package com.untalsanders.contacts.service;

import com.untalsanders.contacts.contact.application.RetrieveContactService;
import com.untalsanders.contacts.contact.domain.model.ContactId;
import com.untalsanders.contacts.contact.web.mapper.ContactWebMapper;
import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.domain.model.Name;
import com.untalsanders.contacts.contact.domain.port.out.ContactRepository;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactWebMapper contactMapper;

    @InjectMocks
    private RetrieveContactService retrieveContactService;

    @Test
    @DisplayName("Should return all contacts")
    void should_return_all_contacts() {
        // Given
        Contact contact1 = new Contact(new ContactId(UUID.randomUUID().toString()), new Name("Sanders", "Gutiérrez"), "1160219207");
        Contact contact2 = new Contact(new ContactId(UUID.randomUUID().toString()), new Name("John", "Doe"), "1234567890");
        ContactResponse response1 = new ContactResponse(UUID.randomUUID().toString(), "Sanders", "Gutiérrez", "1160219207");
        ContactResponse response2 = new ContactResponse(UUID.randomUUID().toString(), "John", "Doe", "1234567890");

        when(contactRepository.findAll()).thenReturn(List.of(contact1, contact2));
        when(contactMapper.toContactResponseCollection(List.of(contact1, contact2)))
                .thenReturn(List.of(response1, response2));

        // When
        Result<List<ContactResponse>> result = retrieveContactService.retrieveAllContacts();

        // Then
        assertTrue(result.isSuccess());
        assertEquals(2, result.getValue().size());
        verify(contactRepository).findAll();
        verify(contactMapper).toContactResponseCollection(List.of(contact1, contact2));
    }

    @Test
    @DisplayName("Should return a contact by id")
    void should_return_contact_by_id() {
        // Given
        UUID id = UUID.randomUUID();
        Contact contact = new Contact(new ContactId(id.toString()), new Name("Sanders", "Gutiérrez"), "1160219207");
        ContactResponse response = new ContactResponse(id.toString(), "Sanders", "Gutiérrez", "1160219207");

        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(contactMapper.toContactResponse(contact)).thenReturn(response);

        // When
        Result<ContactResponse> result = retrieveContactService.findContactById(id);

        // Then
        assertTrue(result.isSuccess());
        assertEquals(response, result.getValue());
        verify(contactRepository).findById(id);
        verify(contactMapper).toContactResponse(contact);
    }

    @Test
    @DisplayName("Should return failure when contact by id not found")
    void should_return_failure_when_contact_not_found() {
        // Given
        UUID id = UUID.randomUUID();
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Result<ContactResponse> result = retrieveContactService.findContactById(id);

        // Then
        assertFalse(result.isSuccess());
        assertEquals(String.format("Contact with id %s not found", id), result.getError());
        verify(contactRepository).findById(id);
        verifyNoInteractions(contactMapper);
    }
}
