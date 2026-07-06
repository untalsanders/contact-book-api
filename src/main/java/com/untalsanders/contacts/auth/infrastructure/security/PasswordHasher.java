package com.untalsanders.contacts.auth.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);

    public String hash(String raw) {
        return ENCODER.encode(raw);
    }

    public boolean matches(String raw, String hash) {
        return ENCODER.matches(raw, hash);
    }
}
