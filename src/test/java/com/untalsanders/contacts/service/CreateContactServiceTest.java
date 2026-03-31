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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private CreateContactService createContactService;

    @Test
    @DisplayName("Should create a contact")
    void should_create_contact() {
        Name name = new Name("Sanders", "Gutiérrez");
        Contact contact = new Contact(1L, name, "1160219207");

        Result<Contact> result = createContactService.createContact(contact);

        assertTrue(result.isSuccess());
        assertNotNull(result.getValue());
        verify(contactRepository).save(contact);
        verifyNoMoreInteractions(contactRepository);
    }
}
