package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.contact.infrastructure.persistence.entity.ContactEntity;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contact.domain.Contact;
import com.untalsanders.contacts.contact.domain.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Mapper(componentModel = "spring")
class ContactMapperTest {
    @Test
    @DisplayName("Should map to contact entity")
    void should_map_to_contact_entity() {
        Name name = new Name("John", "Doe");
        Contact contact = new Contact(UUID.randomUUID(), name, "1123456789");
        ContactEntity contactEntity = ContactMapper.INSTANCE.domainToEntity(contact);
        assertThat(contactEntity).isNotNull();
        assertThat(contactEntity.getFirstname()).isEqualTo(contact.getName().first());
        assertThat(contactEntity.getLastname()).isEqualTo(contact.getName().last());
    }
}
