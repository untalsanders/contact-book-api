package com.untalsanders.contacts.contactbook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Simple POJO domain object representing a contact.
 *
 * @author Sanders Gutiérrez
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private Long id;
    private Name name;
    private String phone;

    public Contact(Name name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", name=" + name +
            ", phone='" + phone + '\'' +
            '}';
    }
}
