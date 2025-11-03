package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.entity.ContactEntity;
import com.untalsanders.contacts.model.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

@Mapper(componentModel = "spring")
class ContactMapperTest {
    @Test
    @DisplayName("Should map to contact entity")
    void should_map_to_contact_entity() {
        Contact contact = new Contact(1L, "Olfre", "1134228486");
        ContactEntity contactEntity = ContactMapper.INSTANCE.domainToEntity(contact);
        assertThat(contactEntity).isNotNull();
        assertThat(contactEntity.getFirstname()).isEqualTo(contact.getFirstname());
        assertThat(contactEntity.getLastname()).isEqualTo(contact.getLastname());
    }
}
