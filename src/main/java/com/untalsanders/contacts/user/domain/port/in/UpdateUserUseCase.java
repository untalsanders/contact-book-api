package com.untalsanders.contacts.user.domain.port.in;

import com.untalsanders.contacts.shared.domain.vo.Result;
import com.untalsanders.contacts.user.domain.model.User;

import java.util.Map;

public interface UpdateUserUseCase {
    Result<User, String> update(String id, User user);
    Result<User, String> partialUpdate(String id, Map<String, String> fields);

}
