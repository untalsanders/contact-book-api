package com.untalsanders.contacts.rest.controller;

import com.untalsanders.contacts.contact.domain.port.in.CreateContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.DeleteContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.RetrieveContactUseCase;
import com.untalsanders.contacts.contact.domain.port.in.UpdateContactUseCase;
import com.untalsanders.contacts.contact.web.controller.ContactController;
import com.untalsanders.contacts.security.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateContactUseCase createContactUseCase;

    @MockitoBean
    private RetrieveContactUseCase retrieveContactUseCase;

    @MockitoBean
    private UpdateContactUseCase updateContactUseCase;

    @MockitoBean
    private DeleteContactUseCase deleteContactUseCase;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @Test
    @DisplayName("Should return unauthorized when accessing contacts without a token")
    void should_return_unauthorized_without_token() throws Exception {
        mockMvc.perform(get("/api/v1/contacts"))
                .andExpect(status().isUnauthorized());
    }
}
