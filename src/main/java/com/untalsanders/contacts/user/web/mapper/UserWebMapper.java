package com.untalsanders.contacts.user.web.mapper;

import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.web.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    @Mapping(target = "id", expression = "java(user.getId() != null ? user.getId().value() : null)")
    UserResponse toUserResponse(User user);
}
