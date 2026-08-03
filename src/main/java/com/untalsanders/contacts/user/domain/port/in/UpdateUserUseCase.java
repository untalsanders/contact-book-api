package com.untalsanders.contacts.user.domain.port.in;

import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.web.dto.UserResponse;

import java.util.Map;

public interface UpdateUserUseCase {
    Result<UserResponse> update(String id, User user);
    Result<UserResponse> partialUpdate(String id, Map<String, String> fields);
}
