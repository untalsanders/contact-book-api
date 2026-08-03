package com.untalsanders.contacts.user.domain.model;

import java.time.Instant;
import java.util.regex.Pattern;

public final class User {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$");

    private UserId id;
    private String email;
    private String password;
    private String username;
    private String firstname;
    private String lastname;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;

    private User() {
    }

    private User(UserId id, String email, String password, String username, String firstname, String lastname,
                Role role, Instant createdAt, Instant updatedAt) {
        this.email = email.trim().toLowerCase();

        validateEmail(this.email);
        validatePassword(password);
        validatePasswordIncludeUsername(password, username);

        this.id = id;
        this.password = password.trim();
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role != null ? role : Role.USER;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("La fecha de actualización no puede ser anterior a la fecha de creación");
        }
    }

    public static User create(UserId id, String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        var cleanEmail = email.trim().toLowerCase();
        validateEmail(cleanEmail);
        validatePassword(password);
        var now = Instant.now();
        return new User(id, cleanEmail, password, generateUsername(cleanEmail), null, null, Role.USER, now, now);
    }

    public static User reconstitute(UserId id, String email, String password, String username, String firstname,
                                     String lastname, Role role, Instant createdAt, Instant updatedAt) {
        if (updatedAt != null && createdAt != null && updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("La fecha de actualización no puede ser anterior a la fecha de creación");
        }
        User user = new User();
        user.id = id;
        user.email = email;
        user.password = password;
        user.username = username;
        user.firstname = firstname;
        user.lastname = lastname;
        user.role = role != null ? role : Role.USER;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del email es inválido");
        }
    }

    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("El formato de la contraseña es inválido");
        }
    }

    private static void validatePasswordIncludeUsername(String password, String username) {
        if (password.toLowerCase().contains(username.toLowerCase())) {
            throw new IllegalArgumentException("La contraseña no puede contener el nombre de usuario");
        }
    }

    private static String generateUsername(String email) {
        String base = email
            .split("@")[0]
            .replaceAll("[^A-Za-z0-9._-]", "")
            .toLowerCase();

        if (base.isBlank()) {
            throw new IllegalArgumentException("No puede generar el nombre de usuario desde el email");
        }

        return base;
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
