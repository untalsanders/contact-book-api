package com.untalsanders.contacts.contactbook.domain.model;

import lombok.*;

import java.util.UUID;

/**
 * Simple POJO domain object representing a contact.
 *
 * @author Sanders Gutiérrez
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private UUID id;
    private Name name;
    private String phone;
}
