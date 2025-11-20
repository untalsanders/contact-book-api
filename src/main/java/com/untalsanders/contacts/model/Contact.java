package com.untalsanders.contacts.model;

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
    private String firstname;
    private String lastname;
    private String phone;

    public Contact(String firstname, String phone) {
        this.firstname = firstname;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
