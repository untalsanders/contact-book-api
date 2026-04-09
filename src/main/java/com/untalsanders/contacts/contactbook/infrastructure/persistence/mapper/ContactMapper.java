package com.untalsanders.contacts.contactbook.infrastructure.persistence.mapper;

import com.untalsanders.contacts.contactbook.infrastructure.persistence.entity.ContactEntity;
import com.untalsanders.contacts.contactbook.domain.model.Contact;
import com.untalsanders.contacts.contactbook.application.dto.request.ContactRequest;
import com.untalsanders.contacts.contactbook.application.dto.response.ContactResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "name.first")
    @Mapping(source = "lastName", target = "name.last")
    @Mapping(source = "phoneNumber", target = "phone")
    Contact toContact(ContactRequest contactRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstname", target = "name.first")
    @Mapping(source = "lastname", target = "name.last")
    @Mapping(source = "phone", target = "phone")
    Contact entityToDomain(ContactEntity contactEntity);

    @InheritInverseConfiguration
    @Mapping(source = "name.first", target = "firstname")
    @Mapping(source = "name.last", target = "lastname")
    @Mapping(source = "phone", target = "phone")
    ContactEntity domainToEntity(Contact contact);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name.first", target = "firstName")
    @Mapping(source = "name.last", target = "lastName")
    @Mapping(source = "phone", target = "phoneNumber")
    ContactResponse toContactResponse(Contact contact);

    List<ContactResponse> toContactResponseCollection(List<Contact> contactList);

    List<Contact> toContacts(List<ContactEntity> contactEntities);
}
