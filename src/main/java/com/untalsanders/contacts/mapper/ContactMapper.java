package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.entity.ContactEntity;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.rest.dto.ContactRequest;
import com.untalsanders.contacts.rest.dto.ContactResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "firstName", target = "name.first")
    @Mapping(source = "lastName", target = "name.last")
    @Mapping(source = "phoneNumber", target = "phone")
    Contact toContact(ContactRequest contactRequest);

    @Mapping(source = "name.first", target = "firstName")
    @Mapping(source = "name.last", target = "lastName")
    @Mapping(source = "phone", target = "phoneNumber")
    ContactResponse toContactResponse(Contact contact);

    List<ContactResponse> toContactResponseCollection(List<Contact> contactList);

    @Mapping(source = "firstname", target = "name.first")
    @Mapping(source = "lastname", target = "name.last")
    Contact entityToDomain(ContactEntity contactEntity);

    List<Contact> toContacts(List<ContactEntity> contactEntities);

    @InheritInverseConfiguration
    ContactEntity domainToEntity(Contact contact);
}
