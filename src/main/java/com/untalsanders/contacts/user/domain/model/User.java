package com.untalsanders.contacts.user.domain.model;

import java.time.Instant;
import java.util.regex.Pattern;

public record User(
    UserId id,
    String email,
    String passwordHash,
    String username,
    String firstname,
    String lastname,
    Role role,
    Instant createdAt,
    Instant updatedAt
) {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");

    public User {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("The email is required");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("The password hash is required");
        }

        if (passwordHash.toLowerCase().contains(username.toLowerCase())) {
            throw new IllegalArgumentException("The password hash cannot contain the username");
        }

        if (role == null) {
            throw new IllegalArgumentException("The user must have at least one role assigned");
        }

        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("The update date cannot be before the creation date");
        }

        email = email.trim().toLowerCase();
        passwordHash = passwordHash.trim();
    }

    public static User create(UserId id, String email, String passwordHash) {
        var now = Instant.now();
        return new User(id, email, passwordHash, generateUsername(email), null, null, Role.USER, now, now);
    }

    private static String generateUsername(String email) {
        String base = email
            .split("@")[0]
            .replaceAll("[^A-Za-z0-9._-]", "")
            .toLowerCase();

        if (base.isBlank()) {
            throw new IllegalArgumentException("Cannot generate username from email");
        }

        return base;
    }
}
