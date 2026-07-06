package com.untalsanders.contacts.user.infrastructure.persistence.mapper;

import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.model.UserId;
import com.untalsanders.contacts.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, UserId.class})
public interface UserMapper {

    @Mapping(target = "id", expression = "java(user.id() != null && user.id().value() != null ? UUID.fromString(user.id().value()) : null)")
    @Mapping(target = "password", source = "passwordHash")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(User user);

    @Mapping(target = "id", expression = "java(new UserId(entity.getId().toString()))")
    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    User toDomain(UserEntity entity);

    List<User> toUsers(List<UserEntity> entities);
}
