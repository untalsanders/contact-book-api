package com.untalsanders.contacts.user.domain.port.in;

public interface DeleteUserUseCase {
    boolean deleteById(String id);
    boolean deleteByEmail(String email);
    boolean deleteByUsername(String username);
}
