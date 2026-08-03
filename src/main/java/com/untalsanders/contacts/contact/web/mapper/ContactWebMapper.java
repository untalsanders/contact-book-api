package com.untalsanders.contacts.contact.web.mapper;

import com.untalsanders.contacts.contact.domain.model.Contact;
import com.untalsanders.contacts.contact.domain.model.ContactId;
import com.untalsanders.contacts.contact.web.dto.ContactRequest;
import com.untalsanders.contacts.contact.web.dto.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface ContactWebMapper {

    ContactWebMapper INSTANCE = Mappers.getMapper(ContactWebMapper.class);

    @Mapping(target = "id", expression = "java(contactRequest.id() != null ? new com.untalsanders.contacts.contact.domain.model.ContactId(contactRequest.id()) : null)")
    @Mapping(target = "name.first", source = "firstName")
    @Mapping(target = "name.last", source = "lastName")
    @Mapping(target = "phone", source = "phoneNumber")
    Contact toContact(ContactRequest contactRequest);

    @Mapping(target = "id", expression = "java(contact.id() != null ? contact.id().value() : null)")
    @Mapping(target = "firstName", source = "name.first")
    @Mapping(target = "lastName", source = "name.last")
    @Mapping(target = "phoneNumber", source = "phone")
    ContactResponse toContactResponse(Contact contact);

    List<ContactResponse> toContactResponseCollection(List<Contact> contactList);
}
