package com.untalsanders.contacts.service;

import com.untalsanders.contacts.contactbook.application.service.DeleteContactService;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.model.Name;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.shared.domain.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private DeleteContactService deleteContactService;

    @Test
    @DisplayName("Should successfully delete a contact")
    void should_successfully_delete_contact() {
        // Given
        Long id = 1L;
        Contact contact = new Contact(id, new Name("Sanders", "Gutiérrez"), "1160219207");
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        // When
        Result<Void> result = deleteContactService.deleteContact(id);

        // Then
        assertTrue(result.isSuccess());
        assertNull(result.getValue());
        verify(contactRepository).findById(id);
        verify(contactRepository).deleteById(id);
    }

    @Test
    @DisplayName("Should return failure when contact to delete is not found")
    void should_return_failure_when_contact_to_delete_not_found() {
        // Given
        Long id = 1L;
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Result<Void> result = deleteContactService.deleteContact(id);

        // Then
        assertFalse(result.isSuccess());
        assertEquals(String.format("Contact with id %s not found", id), result.getError());
        verify(contactRepository).findById(id);
        verify(contactRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return failure when repository throws exception")
    void should_return_failure_when_repository_throws_exception() {
        // Given
        Long id = 1L;
        String errorMessage = "Database error";
        when(contactRepository.findById(id)).thenThrow(new RuntimeException(errorMessage));

        // When
        Result<Void> result = deleteContactService.deleteContact(id);

        // Then
        assertFalse(result.isSuccess());
        assertEquals(errorMessage, result.getError());
        verify(contactRepository).findById(id);
        verify(contactRepository, never()).deleteById(anyLong());
    }
}
