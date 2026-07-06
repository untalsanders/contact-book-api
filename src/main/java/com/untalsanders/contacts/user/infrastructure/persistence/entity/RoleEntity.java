package com.untalsanders.contacts.user.infrastructure.persistence.entity;

import com.untalsanders.contacts.shared.infrastructure.persistence.entity.BaseEntity;
import com.untalsanders.contacts.user.domain.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Role name;
}
