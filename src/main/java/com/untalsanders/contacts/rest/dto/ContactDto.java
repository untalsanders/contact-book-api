package com.untalsanders.contacts.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactDto {
    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String phone;
}
