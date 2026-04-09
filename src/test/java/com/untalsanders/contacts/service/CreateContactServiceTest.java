package com.untalsanders.contacts.service;

import com.untalsanders.contacts.contactbook.application.service.CreateContactService;
import com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.model.Name;
import com.untalsanders.contacts.contactbook.domain.repository.ContactRepository;
import com.untalsanders.contacts.contactbook.application.dto.request.ContactRequest;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
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

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private CreateContactService createContactService;

    @Test
    @DisplayName("Should create a contact")
    void should_create_contact() {
        Name name = new Name("Sanders", "Gutiérrez");
        ContactRequest contactRequest = new ContactRequest("Sanders", "Gutiérrez", "1160219207");
        Contact contact = new Contact(name, "1160219207");
        ContactResponse contactResponse = new ContactResponse(null, "Sanders", "Gutiérrez", "1160219207");

        when(contactMapper.toContact(contactRequest)).thenReturn(contact);
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
