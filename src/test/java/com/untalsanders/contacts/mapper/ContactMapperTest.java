package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.contactbook.infrastructure.persistence.entity.ContactEntity;
import com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper.ContactMapper;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.domain.model.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

@Mapper(componentModel = "spring")
class ContactMapperTest {
    @Test
    @DisplayName("Should map to contact entity")
    void should_map_to_contact_entity() {
        Name name = new Name("John", "Doe");
        Contact contact = new Contact(1L, name, "1123456789");
        ContactEntity contactEntity = ContactMapper.INSTANCE.domainToEntity(contact);
        assertThat(contactEntity).isNotNull();
        assertThat(contactEntity.getFirstname()).isEqualTo(contact.getName().first());
        assertThat(contactEntity.getLastname()).isEqualTo(contact.getName().last());
    }
}
