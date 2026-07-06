package com.untalsanders.contacts.service;

import com.untalsanders.contacts.contact.application.service.CreateContactService;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contact.domain.Contact;
import com.untalsanders.contacts.contact.domain.Name;
import com.untalsanders.contacts.contact.application.port.out.ContactRepository;
import com.untalsanders.contacts.contact.application.dto.ContactRequest;
import com.untalsanders.contacts.contact.application.dto.ContactResponse;
import com.untalsanders.contacts.shared.domain.vo.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private CreateContactService createContactService;

    @Test
    @DisplayName("Should create a contact")
    void should_create_contact() {
        // Given
        Name name = new Name("Sanders", "Gutiérrez");
        ContactRequest contactRequest = new ContactRequest(UUID.randomUUID().toString(), "Sanders", "Gutiérrez", "1160219207");
        Contact contact = new Contact(UUID.randomUUID(), name, "1160219207");
        ContactResponse contactResponse = new ContactResponse(null, "Sanders", "Gutiérrez", "1160219207");

        // When
        when(contactMapper.toContact(contactRequest)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toContactResponse(contact)).thenReturn(contactResponse);

        Result<ContactResponse> result = createContactService.createContact(contactRequest);

        assertTrue(result.isSuccess());
        assertNotNull(result.getValue());
        verify(contactMapper).toContact(contactRequest);
        verify(contactRepository).save(contact);
        verify(contactMapper).toContactResponse(contact);
        verifyNoMoreInteractions(contactRepository, contactMapper);
    }
}
