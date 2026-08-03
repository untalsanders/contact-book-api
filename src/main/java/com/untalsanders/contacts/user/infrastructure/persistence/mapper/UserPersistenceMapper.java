package com.untalsanders.contacts.user.infrastructure.persistence.mapper;

import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.model.UserId;
import com.untalsanders.contacts.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, UserId.class})
public interface UserPersistenceMapper {

    @Mapping(target = "id", expression = "java(user.getId() != null && user.getId().value() != null ? UUID.fromString(user.getId().value()) : null)")
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(User user);

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserId userId = entity.getId() != null ? new UserId(entity.getId().toString()) : null;
        return User.reconstitute(
                userId,
                entity.getEmail(),
                entity.getPassword(),
                entity.getUsername(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    List<User> toUsers(List<UserEntity> entities);
}
