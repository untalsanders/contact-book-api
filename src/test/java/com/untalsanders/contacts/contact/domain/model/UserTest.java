package com.untalsanders.contacts.contact.domain.model;

import com.untalsanders.contacts.user.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Debe crear un usuario válido correctamente usando el Factory Method")
    void shouldCreateValidUser() {
        // Arrange
        var id = "123e4567-e89b-12d3-a456-426614174000";
        var firstname = " Juan ";
        var lastname = " Pérez ";
        var email = " JUan.Perez@Example.com ";
        var password = "SecurePassword123!";

        // Act
        var user = User.create(id, firstname, lastname, email, password);

        // Assert
        assertNotNull(user);
        assertEquals(id, user.id());
        assertEquals("Juan", user.firstname()); // Debe estar trimeado
        assertEquals("Pérez", user.lastname()); // Debe estar trimeado
        assertEquals("juan.perez@example.com", user.email()); // Debe estar trimeado y en minúsculas
        assertEquals("juan.perez", user.username()); // Generado automáticamente
        assertEquals("SecurePassword123!", user.password()); // Debe estar trimeado
        assertTrue(user.roles().contains(Role.USER)); // Debe tener el rol USER por defecto
        assertEquals(1, user.roles().size());
        assertNotNull(user.createdAt());
        assertNotNull(user.updatedAt());
        assertEquals(user.createdAt(), user.updatedAt());
    }

    @Test
    @DisplayName("Debe crear un usuario administrador válido correctamente usando el Factory Method")
    void shouldCreateValidAdminUser() {
        // Arrange
        var id = "123e4567-e89b-12d3-a456-426614174001";
        var firstname = " Admin ";
        var lastname = " User ";
        var email = " admin@example.com ";
        var password = "SecurePass!2";

        // Act
        var user = User.createAdmin(id, firstname, lastname, email, password);

        // Assert
        assertNotNull(user);
        assertTrue(user.roles().contains(Role.USER));
        assertTrue(user.roles().contains(Role.ADMIN));
        assertEquals(2, user.roles().size());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("Debe lanzar excepción si el ID es nulo o en blanco")
    void shouldThrowExceptionWhenIdIsInvalid(String invalidId) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create(invalidId, "Juan", "Perez", "juan@example.com", "password123")
        );
        assertEquals("El ID del usuario no puede ser nulo ni estar vacío", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    @DisplayName("Debe lanzar excepción si el nombre es nulo o en blanco")
    void shouldThrowExceptionWhenFirstnameIsInvalid(String invalidFirstname) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create("id", invalidFirstname, "Perez", "juan@example.com", "password123")
        );
        assertEquals("El nombre no puede ser nulo ni estar vacío", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    @DisplayName("Debe lanzar excepción si el apellido es nulo o en blanco")
    void shouldThrowExceptionWhenLastnameIsInvalid(String invalidLastname) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create("id", "Juan", invalidLastname, "juan@example.com", "password123")
        );
        assertEquals("El apellido no puede ser nulo ni estar vacío", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"juan.example.com", "juan@", "@example.com", "juan@example", "juan@.com"})
    @DisplayName("Debe lanzar excepción si el formato del email es inválido")
    void shouldThrowExceptionWhenEmailFormatIsInvalid(String invalidEmail) {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create("id", "Juan", "Perez", invalidEmail, "password123")
        );
        assertEquals("El formato del email es inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la contraseña contiene el nombre de usuario")
    void shouldThrowExceptionWhenPasswordContainsUsername() {
        // Al crear con email "juan.perez@example.com", el username será "juan.perez"
        var exception = assertThrows(IllegalArgumentException.class, () ->
                User.create("id", "Juan", "Perez", "juan.perez@example.com", "mi_password_juan.Perez_123")
        );
        assertEquals("La contraseña no puede contener el nombre de usuario", exception.getMessage());
    }

    @Test
    @DisplayName("Debe actualizar la información básica del usuario devolviendo una nueva instancia con un nuevo updatedAt")
    void shouldUpdateBasicInfo() {
        // Arrange
        var user = User.create("id", "Juan", "Perez", "juan@example.com", "password123");
        var originalUpdatedAt = user.updatedAt();

        // Pausa pequeña para asegurar que el Instant.now() cambie
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}

        // Act
        var updatedUser = user.update("Carlos", "García", "carlos@example.com");

        // Assert
        assertNotSame(user, updatedUser); // Debe ser una nueva instancia
        assertEquals("id", updatedUser.id());
        assertEquals("Carlos", updatedUser.firstname());
        assertEquals("García", updatedUser.lastname());
        assertEquals("carlos@example.com", updatedUser.email());
        assertEquals("juan", updatedUser.username()); // El username original se mantiene
        assertEquals("password123", updatedUser.password());
        assertEquals(user.roles(), updatedUser.roles());
        assertEquals(user.createdAt(), updatedUser.createdAt());
        assertTrue(updatedUser.updatedAt().isAfter(originalUpdatedAt));
    }

    @Test
    @DisplayName("Debe actualizar la contraseña devolviendo una nueva instancia")
    void shouldUpdatePassword() {
        // Arrange
        var user = User.create("id", "Juan", "Perez", "juan@example.com", "password123");

        // Act
        var updatedUser = user.updatePassword("NewStrongPassword!");

        // Assert
        assertEquals("NewStrongPassword!", updatedUser.password());
        assertTrue(updatedUser.updatedAt().isAfter(user.updatedAt()) || updatedUser.updatedAt().equals(user.updatedAt()));
    }

    @Test
    @DisplayName("Debe actualizar el nombre de usuario devolviendo una nueva instancia")
    void shouldUpdateUsername() {
        // Arrange
        var user = User.create("id", "Juan", "Perez", "juan@example.com", "password123");

        // Act
        var updatedUser = user.updateUsername("juancho.perez");

        // Assert
        assertEquals("juancho.perez", updatedUser.username());
    }

    @Test
    @DisplayName("Debe añadir un rol al usuario devolviendo una nueva instancia")
    void shouldAddRole() {
        // Arrange
        var user = User.create("id", "Juan", "Perez", "juan@example.com", "password123");

        // Act
        var updatedUser = user.addRole(Role.ADMIN);

        // Assert
        assertTrue(updatedUser.roles().contains(Role.USER));
        assertTrue(updatedUser.roles().contains(Role.ADMIN));
        assertEquals(2, updatedUser.roles().size());
        assertTrue(updatedUser.updatedAt().isAfter(user.updatedAt()) || updatedUser.updatedAt().equals(user.updatedAt()));
    }

    @Test
    @DisplayName("Debe remover un rol al usuario devolviendo una nueva instancia")
    void shouldRemoveRole() {
        // Arrange
        var user = User.createAdmin("id", "Admin", "User", "admin@example.com", "SecurePass!1");

        // Act
        var updatedUser = user.removeRole(Role.ADMIN);

        // Assert
        assertTrue(updatedUser.roles().contains(Role.USER));
        assertFalse(updatedUser.roles().contains(Role.ADMIN));
        assertEquals(1, updatedUser.roles().size());
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar remover el único rol del usuario")
    void shouldThrowExceptionWhenRemovingOnlyRole() {
        // Arrange
        var user = User.create("id", "Juan", "Perez", "juan@example.com", "password123");

        // Act & Assert
        var exception = assertThrows(IllegalArgumentException.class, () -> user.removeRole(Role.USER));
        assertEquals("El usuario no puede quedarse sin role", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la fecha de actualización es anterior a la creación")
    void shouldThrowExceptionWhenUpdatedAtIsBeforeCreatedAt() {
        var now = Instant.now();
        var past = now.minus(1, ChronoUnit.DAYS);

        var exception = assertThrows(IllegalArgumentException.class, () ->
                new User("id", "Juan", "Perez", "juan@example.com", "password123", "juan", Set.of(Role.USER), now, past)
        );
        assertEquals("La fecha de actualización no puede ser anterior a la fecha de creación", exception.getMessage());
    }
}
