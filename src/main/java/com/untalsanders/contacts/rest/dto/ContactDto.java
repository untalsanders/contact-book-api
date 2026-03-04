package com.untalsanders.contacts.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactDto {
    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;
}
