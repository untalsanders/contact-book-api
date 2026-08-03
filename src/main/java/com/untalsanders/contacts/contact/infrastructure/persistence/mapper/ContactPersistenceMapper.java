package com.untalsanders.contacts.contact.infrastructure.persistence.mapper;

import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.domain.model.ContactId;
import com.untalsanders.contacts.contact.infrastructure.persistence.entity.ContactEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface ContactPersistenceMapper {

    ContactPersistenceMapper INSTANCE = Mappers.getMapper(ContactPersistenceMapper.class);

    @Mapping(target = "id", expression = "java(contactEntity.getId() != null ? new com.untalsanders.contacts.contact.domain.model.ContactId(contactEntity.getId().toString()) : null)")
    @Mapping(target = "name.first", source = "firstname")
    @Mapping(target = "name.last", source = "lastname")
    @Mapping(target = "phone", source = "phone")
    Contact entityToDomain(ContactEntity contactEntity);

    @InheritInverseConfiguration
    @Mapping(target = "id", expression = "java(contact.id() != null && contact.id().value() != null ? java.util.UUID.fromString(contact.id().value()) : null)")
    @Mapping(target = "firstname", source = "name.first")
    @Mapping(target = "lastname", source = "name.last")
    @Mapping(target = "phone", source = "phone")
    ContactEntity domainToEntity(Contact contact);

    List<Contact> toContacts(List<ContactEntity> contactEntities);
}
