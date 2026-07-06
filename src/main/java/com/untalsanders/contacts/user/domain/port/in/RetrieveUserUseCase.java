package com.untalsanders.contacts.user.domain.port.in;

import com.untalsanders.contacts.shared.domain.vo.Result;
import com.untalsanders.contacts.user.domain.model.User;

import java.util.Set;

public interface RetrieveUserUseCase {
    Result<User, String> getUserById(String id);
    Result<User, String> getUserByEmail(String email);
    Result<User, String> getUserByUsername(String username);
    Result<Set<User>, String> getAllUsers();
}
