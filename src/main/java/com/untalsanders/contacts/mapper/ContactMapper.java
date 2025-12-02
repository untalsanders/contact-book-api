package com.untalsanders.contacts.mapper;

import com.untalsanders.contacts.entity.ContactEntity;
import com.untalsanders.contacts.model.Contact;
import com.untalsanders.contacts.rest.dto.ContactDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(source = "firstname", target = "name.first")
    @Mapping(source = "lastname", target = "name.last")
    Contact toContact(ContactDto contactDto);

    @Mapping(source = "name.first", target = "firstname")
    @Mapping(source = "name.last", target = "lastname")
    ContactDto toContactDto(Contact contact);

    List<ContactDto> toContactDtoCollection(List<Contact> contactList);

    @Mapping(source = "firstname", target = "name.first")
    @Mapping(source = "lastname", target = "name.last")
    Contact entityToDomain(ContactEntity contactEntity);

    List<Contact> toContacts(List<ContactEntity> contactEntities);

    @InheritInverseConfiguration
    ContactEntity domainToEntity(Contact contact);
}
