package com.untalsanders.contacts.rest.dto;

import lombok.Data;

@Data
public class ContactResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
