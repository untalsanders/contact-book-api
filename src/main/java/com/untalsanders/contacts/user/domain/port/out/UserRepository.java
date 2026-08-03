package com.untalsanders.contacts.user.domain.port.out;

import com.untalsanders.contacts.user.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for {@link User} domain objects.
 * All method names are compliant with Spring Data naming conventions, so this interface can easily be extended for Spring Data.
 * See <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">Spring Data JPA Query Methods</a>.
 *
 * @author Sanders Gutiérrez
 */
public interface UserRepository {

    /**
     * Retrieves a user by its unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> findById(String id);

    /**
     * Retrieves a user by its email address.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves a user by its username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists with the given unique identifier.
     *
     * @param id the unique identifier to check
     * @return {@code true} if a user exists with the given id, {@code false} otherwise
     */
    boolean existsById(String id);

    /**
     * Checks if a user exists with the given email address.
     *
     * @param email the email address to check
     * @return {@code true} if a user exists with the given email, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     *
     * @param username the username to check
     * @return {@code true} if a user exists with the given username, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Retrieves all users.
     *
     * @return a {@link Set} containing all users
     */
    List<User> findAll();

    /**
     * Saves a new user or updates an existing one.
     *
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);

    /**
     * Updates an existing user with the given unique identifier.
     *
     * @param id   the unique identifier of the user to update
     * @param user the user data to update
     * @return the updated user
     */
    User update(String id, User user);

    /**
     * Deletes a user by its unique identifier.
     *
     * @param id the unique identifier of the user to delete
     * @return {@code true} if the user was deleted, {@code false} otherwise
     */
    boolean deleteById(String id);

    /**
     * Deletes a user by its email address.
     *
     * @param email the email address of the user to delete
     * @return {@code true} if the user was deleted, {@code false} otherwise
     */
    boolean deleteByEmail(String email);

    /**
     * Deletes a user by its username.
     *
     * @param username the username of the user to delete
     * @return {@code true} if the user was deleted, {@code false} otherwise
     */
    boolean deleteByUsername(String username);
}
