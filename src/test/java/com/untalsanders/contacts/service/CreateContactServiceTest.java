package com.untalsanders.contacts.service;

import com.untalsanders.contacts.repository.ContactRepository;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.service.CreateContactService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CreateContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private CreateContactService createContactService;

    @Test
    @DisplayName("Should create a contact")
    void should_create_contact() {
        Contact contact = new Contact(1L, "Sanders", "Gutiérrez", "1160219207");
        createContactService.createContact(contact);
        verify(contactRepository).save(contact);
        verifyNoInteractions(contactRepository);
    }
}
