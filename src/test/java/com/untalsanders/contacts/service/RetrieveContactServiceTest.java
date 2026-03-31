package com.untalsanders.contacts.service;

import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.model.Name;
import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrieveContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private RetrieveContactService retrieveContactService;

    @Test
    @DisplayName("Should return all contacts")
    void should_return_all_contacts() {
        // Given
        Contact contact1 = new Contact(1L, new Name("Sanders", "Gutiérrez"), "1160219207");
        Contact contact2 = new Contact(2L, new Name("John", "Doe"), "1234567890");
        when(contactRepository.findAll()).thenReturn(List.of(contact1, contact2));

        // When
        Result<List<Contact>> result = retrieveContactService.getContacts();

        // Then
        assertTrue(result.isSuccess());
        assertEquals(2, result.getValue().size());
        verify(contactRepository).findAll();
    }

    @Test
    @DisplayName("Should return a contact by id")
    void should_return_contact_by_id() {
        // Given
        Long id = 1L;
        Contact contact = new Contact(id, new Name("Sanders", "Gutiérrez"), "1160219207");
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        // When
        Result<Contact> result = retrieveContactService.getContact(id);

        // Then
        assertTrue(result.isSuccess());
        assertEquals(contact, result.getValue());
        verify(contactRepository).findById(id);
    }

    @Test
    @DisplayName("Should return failure when contact by id not found")
    void should_return_failure_when_contact_not_found() {
        // Given
        Long id = 1L;
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Result<Contact> result = retrieveContactService.getContact(id);

        // Then
        assertFalse(result.isSuccess());
        assertEquals(String.format("Contact with id %s not found", id), result.getError());
        verify(contactRepository).findById(id);
    }
}
