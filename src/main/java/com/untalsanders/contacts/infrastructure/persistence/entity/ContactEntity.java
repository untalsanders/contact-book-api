package com.untalsanders.contacts.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class ContactEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String firstname;

    private String lastname;

    @Column(nullable = false, unique = true)
    private String phone;

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + this.getId() +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
