package com.untalsanders.contacts.contact.domain.model;

import com.untalsanders.contacts.user.domain.model.Role;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.model.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Debe crear un usuario válido correctamente usando el Factory Method")
    void shouldCreateValidUser() {
        // Arrange
        var id = UUID.randomUUID().toString();
        var email = " JUan.Perez@Example.com ";
        var password = "SecurePassword123!";

        // Act
        var user = User.create(new UserId(id), email, password);

        // Assert
        assertNotNull(user);
        assertEquals(id, user.getId().value());
        assertEquals("juan.perez@example.com", user.getEmail());
        assertEquals("juan.perez", user.getUsername()); // Generado automáticamente
        assertEquals("SecurePassword123!", user.getPassword());
        assertEquals(Role.USER, user.getRole()); // Rol por defecto
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(user.getCreatedAt(), user.getUpdatedAt());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("Debe lanzar excepción si el ID es nulo o en blanco")
    void shouldThrowExceptionWhenIdIsInvalid(String invalidId) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create(new UserId(invalidId), "juan@example.com", "password123")
        );
        assertEquals("El ID del usuario no puede ser nulo ni estar vacío", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan.example.com", "juan@", "@example.com", "juan@example", "juan@.com"})
    @DisplayName("Debe lanzar excepción si el formato del email es inválido")
    void shouldThrowExceptionWhenEmailFormatIsInvalid(String invalidEmail) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create(new UserId(UUID.randomUUID().toString()), invalidEmail, "password123")
        );
        assertEquals("El formato del email es inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la fecha de actualización es anterior a la creación")
    void shouldThrowExceptionWhenUpdatedAtIsBeforeCreatedAt() {
        var now = Instant.now();
        var past = now.minus(1, ChronoUnit.DAYS);

        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.reconstitute(new UserId(UUID.randomUUID().toString()), "juan@example.com", "password123", "juan", "Juan", "Perez", Role.USER, now, past)
        );
        assertEquals("La fecha de actualización no puede ser anterior a la fecha de creación", exception.getMessage());
    }
}
