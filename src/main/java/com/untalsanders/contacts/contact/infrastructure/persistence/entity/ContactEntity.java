package com.untalsanders.contacts.contact.infrastructure.persistence.entity;

import com.untalsanders.contacts.shared.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
@ToString
@Builder
public class ContactEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String firstname;

    private String lastname;

    @Column(nullable = false, unique = true)
    private String phone;
}
