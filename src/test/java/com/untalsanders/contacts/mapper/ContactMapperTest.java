package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.contact.domain.model.ContactId;
import com.untalsanders.contacts.contact.infrastructure.persistence.entity.ContactEntity;
import com.untalsanders.contacts.contact.infrastructure.persistence.mapper.ContactPersistenceMapper;
import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.domain.model.Name;
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
        Contact contact = new Contact(new ContactId(UUID.randomUUID().toString()), name, "1123456789");
        ContactEntity contactEntity = ContactPersistenceMapper.INSTANCE.domainToEntity(contact);
        assertThat(contactEntity).isNotNull();
        assertThat(contactEntity.getFirstname()).isEqualTo(contact.name().first());
        assertThat(contactEntity.getLastname()).isEqualTo(contact.name().last());
    }
}
