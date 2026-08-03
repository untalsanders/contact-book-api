package com.untalsanders.contacts.user.domain.port.in;

import com.untalsanders.contacts.shared.domain.Result;
import com.untalsanders.contacts.user.web.dto.UserResponse;

import java.util.List;

/**
 * Provides operations to fetch users by various identifiers and retrieve all users.
 */
public interface RetrieveUserUseCase {

    /**
     * Retrieves a user by their unique identifier.
     * @param id the unique identifier of the user to retrieve
     * @return a Result containing the User if found, or an error message if not found
     */
    Result<UserResponse> findUserById(String id);

    /**
     * Retrieves a user by their email address.
     * @param email the email address of the user to retrieve
     * @return a Result containing the User if found, or an error message if not found
     */
    Result<UserResponse> getUserByEmail(String email);

    /**
     * Retrieves a user by their username.
     * @param username the username of the user to retrieve
     * @return a Result containing the User if found, or an error message if not found
     */
    Result<UserResponse> getUserByUsername(String username);

    /**
     * Retrieves all users in the system.
     * @return a Result containing a Set of all users, or an error message if the operation fails
     */
    Result<List<UserResponse>> retrieveAllUsers();
}
