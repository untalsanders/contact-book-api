package com.untalsanders.contacts.auth.domain.port.in;

public interface RegisterUseCase {
    record RegisterResult(String userId, String email) {}

    RegisterResult register(String email, String rawPassword);
}
